package com.yws.cf;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompletableFutureAPIDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> {
            //暂停几秒
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "abc";
        });

        //暂停几秒
        try { TimeUnit.SECONDS.sleep(1); } catch (Exception e){ e.printStackTrace(); }

        //System.out.println(supplyAsync.get());//不见不散
        //System.out.println(supplyAsync.get(2, TimeUnit.SECONDS));//过时不候
        //System.out.println(supplyAsync.join());//不用异常处理
        //System.out.println(supplyAsync.getNow("xxx"));//处理完成返回结果值，如果还没处理完，返回参数值
        System.out.println(supplyAsync.complete("completeValue") + "\t" +  supplyAsync.join());//同上，能够得到处理完成标志，true-使用参数值，false-返回计算值

    }
}
