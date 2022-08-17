package com.yws.rwlock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 锁降级；遵循获取写锁->再获取读锁->再释放写锁的次序，写锁能够降级为读锁。
 * 如果一个线程占有了写锁，在不释放写锁的情况下，它还能占有读锁，即写锁降级为读锁。
 * 读没有完成时候写锁无法获得锁，必须要等着读锁读完后才有机会写
 */
public class LockDownGradingDemo {
    public static void main(String[] args) {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

        //==========正常场景===========
/*        //线程A
        readLock.lock();
        System.out.println("读操作");
        readLock.unlock();
        //线程B
        writeLock.lock();
        System.out.println("写操作");
        writeLock.unlock();*/

        //==========写锁降级===========
/*        writeLock.lock();
        System.out.println("写操作");

        readLock.lock();
        System.out.println("读操作");

        writeLock.unlock();
        readLock.unlock();*/


        //==========读锁不能升级===========
        readLock.lock();
        System.out.println("读操作");

        writeLock.lock();
        System.out.println("写操作");

        readLock.unlock();
        writeLock.unlock();


    }
}
