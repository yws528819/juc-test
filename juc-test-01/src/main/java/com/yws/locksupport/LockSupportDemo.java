package com.yws.locksupport;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.TimeUnit.SECONDS;

public class LockSupportDemo {
    public static void main(String[] args) {
        /**
         * 1.正常+无锁块要求
         * 2.可以先唤醒后等待，也可以先等待后唤醒
         * 3.也需要成双成对出现
         */
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ----come in");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "\t ----被唤醒");
        }, "t1");
        t1.start();

        //暂停几秒钟
        try { SECONDS.sleep(1); } catch (Exception e){ e.printStackTrace(); }
        new Thread(() -> {
            LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName() + "\t ----发出通知");
        }, "t1").start();
    }

    private static void lockAwaitSignal() {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        /**
         * 1.Condition的await和signal需要在lock和unlock里，否则会报异常
         * 2.先await再signal，不能反，否则结束不了
         */
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t ---come in");
                condition.await();
                System.out.println(Thread.currentThread().getName() + "\t ---被唤醒");
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        }, "t1").start();

        //暂停几秒钟
        try { SECONDS.sleep(1); } catch (Exception e){ e.printStackTrace(); }
        new Thread(() -> {
            lock.lock();
            try{
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "\t ---发出通知");
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }, "t2").start();
    }


    private static void syncWaitNotify() {
        /**
         * 1.wait/notify 必须在synchronized里使用，不然会报错，且成对出现使用
         * 2.先wait再notify，不然程序无法正常结束
         */
        Object objectLock = new Object();

        new Thread(() -> {
            synchronized (objectLock) {
                System.out.println(Thread.currentThread().getName() + "\t -----come in");
                try {
                    objectLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t ------被唤醒");
            }
        }, "t1").start();

        //暂停几秒钟
        try { SECONDS.sleep(1); } catch (Exception e){ e.printStackTrace(); }
        new Thread(() -> {
            synchronized (objectLock) {
                objectLock.notify();
                System.out.println(Thread.currentThread().getName() + "\t -----发出通知");
            }
        }, "t2").start();
    }
}
