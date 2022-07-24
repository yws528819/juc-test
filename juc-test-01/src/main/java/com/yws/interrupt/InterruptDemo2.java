package com.yws.interrupt;


import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class InterruptDemo2 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "\t 中断标志位：" + Thread.currentThread().isInterrupted()
                            + "程序停止");
                    break;
                }
                //暂停几毫秒
                try {
                    MILLISECONDS.sleep(20);
                } catch (Exception e) {
                    Thread.currentThread().interrupt();//为什么要在异常处，再调用一次？
                    e.printStackTrace();
                }
                System.out.println("---- hello InterruptDemo2");
            }
        }, "t1");

        t1.start();

        //暂停几秒钟
        try { TimeUnit.SECONDS.sleep(1); } catch (Exception e){ e.printStackTrace(); }

        new Thread(() -> t1.interrupt(), "t2").start();
    }
}
