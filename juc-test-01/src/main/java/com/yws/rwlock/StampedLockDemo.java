package com.yws.rwlock;

import java.util.concurrent.locks.StampedLock;

import static java.util.concurrent.TimeUnit.SECONDS;

public class StampedLockDemo {

    static int number = 37;
    static StampedLock stampedLock = new StampedLock();

    public void write() {
        long stamped = stampedLock.writeLock();
        System.out.println(Thread.currentThread().getName() + "\t 写线程准备修改");
        try {
            number = number + 13;
        }finally {
            stampedLock.unlockWrite(stamped);
        }
        System.out.println(Thread.currentThread().getName() + "\t 写线程结束修改");
    }

    public void read() {
        long stamped = stampedLock.readLock();
        System.out.println(Thread.currentThread().getName() + "\t come in read code block, 4 seconds continue ...");
        for (int i = 1; i <= 4; i++) {
            //暂停几秒钟
            try { SECONDS.sleep(1); } catch (Exception e){ e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName() + "\t 正在读取中......");
        }

        try{
            int result = number;
            System.out.println(Thread.currentThread().getName() + "\t 获得成员变量值result:" + result);
            System.out.println("预期结果：写线程没有修改成功，读锁时候写锁无法介入，传统的读写互斥");
        }finally {
            stampedLock.unlockRead(stamped);
        }
    }


    public static void main(String[] args) {
        StampedLockDemo stampedLockDemo = new StampedLockDemo();
        new Thread(() -> {
            stampedLockDemo.read();
        }, "readThread").start();

        //暂停几秒钟
        try { SECONDS.sleep(1); } catch (Exception e){ e.printStackTrace(); }

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t -----come in");
            stampedLockDemo.write();
        }, "writeThread").start();

        //暂停几秒钟
        try { SECONDS.sleep(4); } catch (Exception e){ e.printStackTrace(); }

        System.out.println(Thread.currentThread().getName() + "\t numbber:" + number);
    }
}
