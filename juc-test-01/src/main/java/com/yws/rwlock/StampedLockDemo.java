package com.yws.rwlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * StampedLock = ReentrantReadWriteLock + 读的过程中也允许写锁介入
 */
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

    //悲观读，读没有完成时候写锁无法获得锁
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

    //乐观读，读的过程中也允许获取写锁介入
    public void tryOptimisticRead() {
        long stamp = stampedLock.tryOptimisticRead();
        int result = number;
        //故意间隔4秒，很乐观认为读取中没有其他线程改过number值，具体靠判断
        System.out.println("4秒前stampedLock.validate方法返回值（true无修改，false-有修改）\t" + stampedLock.validate(stamp));
        for (int i = 1; i <= 4 ; i++) {
            //暂停几秒线程
            try{ SECONDS.sleep(1); }catch (Exception e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName() + "\t 正在读取..."
                    + i + "秒后stampedLock.validate方法返回值（true无修改，false-有修改）\t" + stampedLock.validate(stamp));
        }

        if (!stampedLock.validate(stamp)) {
            System.out.println("有人修改过----有写操作");
            long readStamp = stampedLock.readLock();
            try {
                System.out.println("从乐观读 升级为 悲观读");
                result = number;
                System.out.println("重新悲观读后result:" + result);
            }finally {
                stampedLock.unlockRead(readStamp);
            }
        }
        System.out.println(Thread.currentThread().getName() + "\t finally value:" + number);


    }




    public static void main(String[] args) {
        StampedLockDemo stampedLockDemo = new StampedLockDemo();


/*      传统读版
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

        System.out.println(Thread.currentThread().getName() + "\t numbber:" + number);*/


        new Thread(() -> {
            stampedLockDemo.tryOptimisticRead();
        }, "readThread").start();

        //暂停2秒线程，读过程可以写介入，演示
        try{ SECONDS.sleep(2); }catch (Exception e) { e.printStackTrace(); }

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t -----come in");
            stampedLockDemo.write();
        }, "writeThread").start();


    }
}
