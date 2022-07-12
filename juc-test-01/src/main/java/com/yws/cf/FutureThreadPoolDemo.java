package com.yws.cf;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureThreadPoolDemo {
    public static void main(String[] args) throws Exception{
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        long startTime = System.currentTimeMillis();

        FutureTask<String> futureTask1 = new FutureTask<>(() -> {
            return "task1 over";
        });
        FutureTask<String> futureTask2 = new FutureTask<>(() -> {
            return "task2 over";
        });

        threadPool.submit(futureTask1);
        threadPool.submit(futureTask2);

        futureTask1.get();
        futureTask2.get();

        long endTime = System.currentTimeMillis();

        System.out.println("耗时：" + (endTime - startTime) + "毫秒");

        threadPool.shutdown();
    }
}
