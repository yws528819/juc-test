package com.yws.cf;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureAPIDemo3 {
    public static void main(String[] args) {
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenRun(() -> {}).join());
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenAccept(System.out::println).join());
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenApply(r -> r + "resultB").join());
    }
}
