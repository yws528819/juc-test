package com.yws.cf;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureAPIDemo2 {
    public static void main(String[] args) throws IOException {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("111");
           //暂停几秒钟
           try { TimeUnit.SECONDS.sleep(1); } catch (Exception e){ e.printStackTrace(); }
           return 1;
        }).thenApply(f -> {
            System.out.println("222");
            return f + 2;
        }).handle((f, e) -> {
            System.out.println("333");
            return f + 3;
        }).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println(v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        });

        System.out.println(Thread.currentThread().getName() + "---主线程先去忙其他任务");

        System.in.read();
    }
}
