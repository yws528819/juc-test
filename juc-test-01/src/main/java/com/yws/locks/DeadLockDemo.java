package com.yws.locks;

import java.util.concurrent.TimeUnit;

public class DeadLockDemo {
    public static void main(String[] args) {
        final Object objectA = new Object();
        final Object objectB = new Object();

        new Thread(()-> {
            synchronized (objectA) {
                System.out.println(Thread.currentThread().getName() + "持有A锁，希望获得B锁");
                //暂停几秒钟
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (objectB) {
                    System.out.println(Thread.currentThread().getName() + "成功获得B锁");
                }
            }
        }, "A").start();

        new Thread(()-> {
            synchronized (objectB) {
                System.out.println(Thread.currentThread().getName() + "持有B锁，希望获得A锁");
                //暂停几秒钟
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (objectA) {
                    System.out.println(Thread.currentThread().getName() + "成功获得A锁");
                }
            }
        }, "B").start();

    }
}
