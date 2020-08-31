package org.example.thread;

import lombok.extern.log4j.Log4j2;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 让线程同步执行
 * <p>
 * 方法一、使用线程池的方式
 */

@Log4j2
public class ThreadTest {

    public static void mapTest() {
        Map<String, String> m = new HashMap<>();

        Map<String, String> mm = Collections.synchronizedMap(m);
        mm.put("1", "1");

        Map<String, String> cm = new ConcurrentHashMap<>();
        cm.put("1", "1");
    }

    private static void doSomething(int n) throws InterruptedException {
        log.info("thread no = {} start", n);
        Thread.sleep(1000);
        log.info("thread no = {} end", n);
    }

    public static void orderRun() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        for (int idx = 0; idx < 3; idx++) {

            int finalIdx = idx;
            executor.submit(() -> {
                try {
                    doSomething(finalIdx);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void main(String[] args) throws InterruptedException {
        orderRun();
    }
}
