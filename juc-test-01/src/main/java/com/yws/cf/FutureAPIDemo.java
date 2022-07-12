package com.yws.cf;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureAPIDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            //暂停几秒线程
            System.out.println(Thread.currentThread().getName() + "\t ---- come in");
            //暂停几秒线程
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "task over";
        });

        new Thread(futureTask).start();

        System.out.println(Thread.currentThread().getName() + "\t -----忙其他任务了");

        //System.out.println(futureTask.get());

        //get()带超时时间，超时报异常
        //System.out.println(futureTask.get(3, TimeUnit.SECONDS));

        //isDone()轮询
        while (true) {
            if (futureTask.isDone()) {
                System.out.println(futureTask.get());
                break;
            }else {
                //暂停毫秒
                try{ TimeUnit.MICROSECONDS.sleep(500); }catch (Exception e) { e.printStackTrace(); }
                System.out.println("正在处理中，不要再催了，越催越慢，再催熄火");
            }
        }

    }
}
