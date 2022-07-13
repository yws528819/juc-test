package com.yws.cf;

import java.util.concurrent.*;

public class CompletableFutureUseDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        try {
            CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName() + "----come in");
                int result = ThreadLocalRandom.current().nextInt(10);

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("---1秒后出结果：" + result);
                if (result > 1) {
                    int i = 10/0;
                }

                return result;
            }, threadPool).whenComplete((v, e) -> {
                System.out.println(Thread.currentThread().getName() + "----come in");
                if (e == null) {
                    System.out.println("---计算完成，更新系统updateValue:" + v);
                }
            }).exceptionally(e -> {
                e.printStackTrace();
                System.out.println("异常情况：" + e.getCause() + "\t" + e.getMessage());
                return null;
            });

            System.out.println(Thread.currentThread().getName() + "线程先去忙其他任务");

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }

        //使用自定义线程池，就不用考虑以下代码
        //主线程不要立刻结束，否则CompletableFuture默认使用的线程会立刻关闭，暂停3秒钟
        //try { TimeUnit.SECONDS.sleep(3); } catch (Exception e){ e.printStackTrace(); }
    }



    private static void future1() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "----come in");
            int result = ThreadLocalRandom.current().nextInt(10);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("---1秒后出结果：" + result);
            return result;
        });

        System.out.println(Thread.currentThread().getName() + "线程先去忙其他任务");

        System.out.println(completableFuture.get());
    }
}
