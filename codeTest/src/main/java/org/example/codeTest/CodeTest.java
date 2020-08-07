package org.example.codeTest;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Log4j2
public class CodeTest {
//    private ReentrantLock reentrantLock = new ReentrantLock();

    private synchronized void syncTest() throws InterruptedException {
        log.info("测试 synchronized 修饰方式");
        Thread.sleep(2000);
    }

    private void noSyncTest() throws InterruptedException {
        long beginTime = System.currentTimeMillis();
        log.info("当前线程 - {} 等待执行", Thread.currentThread().getId());

        synchronized (this) {
            synchronized (this) {
//                reentrantLock.lock();
                log.info("测试非 synchronized 修饰方式");
                Thread.sleep(2000);
//                reentrantLock.unlock();
            }
        }
        log.info("当前线程 - {} 执行结束, 耗时 = {}", Thread.currentThread().getId(), System.currentTimeMillis() - beginTime);
    }

    private void learningList() {
        List<Integer> node = Lists.newArrayList();

        node.add(3);
        node.add(5);

        log.info("node = {}", JSON.toJSONString(node));
    }

    private void learningStack() {
        Stack<Integer> stack = new Stack<>();

        stack.add(3);
        stack.add(0, 5);

        stack.push(6);
        Integer peekTesT = stack.peek();
        stack.pop();

        log.info("node = {}, peekTesT = {}", JSON.toJSONString(stack), peekTesT);
    }

    private void learningSet() {
        Set<Integer> set = new ConcurrentSkipListSet<>();

        set.add(10);
        set.add(30);
        set.add(50);

        set.contains(30);
    }

    private void learningMap() {
//        Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, Integer> map = new ConcurrentHashMap<>();

        map.put(1, 1);
        map.put(2, 2);
        map.put(1, 3);

        map.containsKey(2);
    }

    private void testJUC() throws InterruptedException {
        CodeTest codeTest = new CodeTest();

        // 信号量
        Semaphore semaphore = new Semaphore(1);

        // 设置障碍点
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> log.info("屏障Run 当前线程ID = {}", Thread.currentThread().getId()));

        // 线程等待
        final CountDownLatch countDownLatch = new CountDownLatch(5);

        ReentrantLock reentrantLock = new ReentrantLock();

        for (int idx = 0; idx < 5; idx++) {
            new SimpleAsyncTaskExecutor("test-simple").submit(() -> {
                try {
                    log.info("线程尝试加锁 = {}", Thread.currentThread().getId());
                    reentrantLock.tryLock(1, TimeUnit.SECONDS);
                    log.info("线程尝试加锁成功 = {}", Thread.currentThread().getId());
                } catch (InterruptedException e) {
                    log.info("尝试加锁失败, e=", e);
                }

                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    log.info("信号量获取失败");
                }

                try {
                    codeTest.noSyncTest();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();

                    semaphore.release();

                    countDownLatch.countDown();
                    try {
                        log.info("设置屏障点, ID={}", Thread.currentThread().getId());
                        cyclicBarrier.await();
                        log.info("屏障结束, ID={}", Thread.currentThread().getId());
                    } catch (InterruptedException | BrokenBarrierException e) {
                        log.info("设置屏障点异常, e=", e);
                    }
                }
            });
        }

//        for (int idx = 0; idx < 5; idx++) {
//            long beginTime = System.currentTimeMillis();
//            log.info("当前线程 - {} 等待执行", Thread.currentThread().getId());
//            codeTest.syncTest();
//            log.info("当前线程 - {} 执行结束, 耗时 = {}", Thread.currentThread().getId(), System.currentTimeMillis() - beginTime);
//        }

        log.info("等待线程执行结束...");
        countDownLatch.await(4, TimeUnit.SECONDS);
        log.info("all done");
    }

    private void testRLock() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);

        Lock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();


        for (int idx = 0; idx < 5; idx++) {
            new SimpleAsyncTaskExecutor("test-RLock").submit(() -> {
                try {
                    boolean success = reentrantLock.tryLock(1, TimeUnit.SECONDS);
                    log.info("锁的状态 = {}, ThreadID={}", success, Thread.currentThread().getId());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                log.info("do something = {}", Thread.currentThread().getId());

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                log.info("释放锁 = {}", Thread.currentThread().getId());
                reentrantLock.unlock();
                log.info("释放锁成功 = {}", Thread.currentThread().getId());

                countDownLatch.countDown();
            });
        }

        log.info("等待线程全部释放...");
        countDownLatch.await();
        log.info("线程全部释放");
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CodeTest codeTest = new CodeTest();
        codeTest.learningMap();
//        codeTest.learningSet();
//        codeTest.learningStack();
//        codeTest.learningList();

        codeTest.testRLock();
    }
}
