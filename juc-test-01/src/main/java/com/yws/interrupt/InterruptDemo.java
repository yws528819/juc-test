package com.yws.interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class InterruptDemo {

    static volatile boolean isStop = false;
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "\t isInterrupted 被修改为true，程序停止");
                    break;
                }
                System.out.println("t1---- hello interrupt api");
            }
        }, "t1");
        t1.start();


        //暂停几毫秒线程
        try{ TimeUnit.MICROSECONDS.sleep(20); }catch (Exception e) { e.printStackTrace(); }

        new Thread(() -> {
           t1.interrupt();
        }, "t2").start();

        //也可以主线程里直接中断
        //t1.interrupt();
    }

    private static void m2_atomicBoolean() {
        new Thread(() -> {
            while (true) {
                if (atomicBoolean.get()) {
                    System.out.println(Thread.currentThread().getName() + "\t atomicBoolean 被修改为true，程序停止");
                    break;
                }
                System.out.println("t1---- hello atomicBoolean");
            }
        }, "t1").start();

        //暂停几毫秒线程
        try{ TimeUnit.MICROSECONDS.sleep(20); }catch (Exception e) { e.printStackTrace(); }

        new Thread(() -> {
            atomicBoolean.set(true);
        }, "t2").start();
    }

    private static void m1() {
        new Thread(() -> {
            while (true) {
                if (isStop) {
                    System.out.println(Thread.currentThread().getName() + "\t isStop 被修改为true，程序停止");
                    break;
                }
                System.out.println("t1---- hello volatile");
            }
        }, "t1").start();

        //暂停几毫秒线程
        try{ TimeUnit.MICROSECONDS.sleep(20); }catch (Exception e) { e.printStackTrace(); }

        new Thread(() -> {
            isStop = true;
        }, "t2").start();
    }
}
