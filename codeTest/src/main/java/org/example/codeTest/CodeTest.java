package org.example.codeTest;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Log4j2
public class CodeTest {

    private synchronized void syncTest() throws InterruptedException {
        log.info("测试 synchronized 修饰方式");
        Thread.sleep(2000);
    }

    private void noSyncTest() throws InterruptedException {
        long beginTime = System.currentTimeMillis();
        log.info("当前线程 - {} 等待执行", Thread.currentThread().getId());
        synchronized (this) {
            synchronized (this) {
                log.info("测试非 synchronized 修饰方式");
                Thread.sleep(2000);
            }
        }
        log.info("当前线程 - {} 执行结束, 耗时 = {}", Thread.currentThread().getId(), System.currentTimeMillis() - beginTime);
    }

    private void learningList() {
        List<Integer> node = Lists.newArrayList();

        node.add(3);

        log.info("node = {}", JSON.toJSONString(node));
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CodeTest codeTest = new CodeTest();
        codeTest.learningList();

        List<Future<?>> futureList = Lists.newArrayListWithCapacity(5);

        for (int idx = 0; idx < 5; idx++) {
            futureList.add(new SimpleAsyncTaskExecutor("test-simple").submit(() -> {
                try {
                    codeTest.noSyncTest();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
        }

        for (int idx = 0; idx < 5; idx++) {
            long beginTime = System.currentTimeMillis();
            log.info("当前线程 - {} 等待执行", Thread.currentThread().getId());
            codeTest.syncTest();
            log.info("当前线程 - {} 执行结束, 耗时 = {}", Thread.currentThread().getId(), System.currentTimeMillis() - beginTime);
        }

        futureList.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }
}
