package com.yws.cf;

import java.util.concurrent.*;

public class CompletableFutureWithThreadPoolDemo {
    public static void main(String[] args){
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        try {
            CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
                System.out.println("1号任务" + "\t" + Thread.currentThread().getName());
                return "abc";
            }).thenRun(() -> {
                System.out.println("2号任务" + "\t" + Thread.currentThread().getName());
            }).thenRun(() -> {
                System.out.println("3号任务" + "\t" + Thread.currentThread().getName());
            }).thenRun(() -> {
                System.out.println("4号任务" + "\t" + Thread.currentThread().getName());
            });
            System.out.println(completableFuture.get(2, TimeUnit.SECONDS));
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }

    }
}
