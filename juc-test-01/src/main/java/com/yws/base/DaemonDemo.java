package com.yws.base;


import java.util.concurrent.TimeUnit;

public class DaemonDemo {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 开始运行，" + ((Thread.currentThread().isDaemon()) ? "守护线程" : "用户线程"));
            while (true) {

            }
        }, "T1");
        t1.setDaemon(true);
        t1.start();

        //暂停几秒线程
        try{ TimeUnit.SECONDS.sleep(3); }catch (Exception e) { e.printStackTrace(); }

        System.out.println(Thread.currentThread().getName() + "\t---end 主线程");
    }
}
