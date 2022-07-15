package com.yws.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureCombineDemo {
    public static void main(String[] args) {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ---启动");
            //暂停几秒钟
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 10;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ---启动");
            //暂停几秒钟
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 20;
        });

        CompletableFuture<Integer> future = future1.thenCombine(future2, (f1, f2) -> {
            System.out.println("-----开始两个结果合并");
            return f1 + f2;
        });

        System.out.println(future.join());
    }
}
