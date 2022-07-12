package com.yws.cf;


import java.util.concurrent.FutureTask;

public class CompletableFutureDemo {
    public static void main(String[] args) throws Exception{
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            return "水";
        });
        new Thread(futureTask, "t1").start();

        System.out.println(futureTask.get());

    }
}
