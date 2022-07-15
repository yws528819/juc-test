package com.yws.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureFastDemo {
    public static void main(String[] args) {
        CompletableFuture<String> playA = CompletableFuture.supplyAsync(() -> {
            System.out.println("----A come in");
            //暂停几秒钟
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "PlayA";
        });

        CompletableFuture<String> playB = CompletableFuture.supplyAsync(() -> {
            System.out.println("----B come in");
            //暂停几秒钟
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "PlayB";
        });

        CompletableFuture<String> result = playA.applyToEither(playB, f -> f + " is winner");

        System.out.println(Thread.currentThread().getName() + "\t ------:" + result.join());
    }
}
