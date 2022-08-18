package com.yws.rwlock;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockDownTest {
    public static void main(String[] args) {
        A a = new A();
        new Thread(() -> {
            a.method1();
        }, "t1").start();

        //暂停几秒线程
        try{ TimeUnit.SECONDS.sleep(5); }catch (Exception e) { e.printStackTrace(); }
        System.out.println("------------------5s后，t2起飞-------------------");

        new Thread(() -> {
            a.method2();
        }, "t2").start();
    }



}


class A {
    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void method1() {
        readWriteLock.writeLock().lock();
        readWriteLock.readLock().lock();


        readWriteLock.writeLock().unlock();

        //********降级需要等写锁释放了，其他线程才能拿到读锁************
        System.out.println(Thread.currentThread().getName() + "\t 锁降级了，写锁已释放，我睡10s");
        //暂停几秒线程
        try{ TimeUnit.SECONDS.sleep(10); }catch (Exception e) { e.printStackTrace(); }
        System.out.println(Thread.currentThread().getName() + "\t 我醒了，没在等我吧...");

        readWriteLock.readLock().unlock();
        System.out.println(Thread.currentThread().getName() + "\t 运行结束");
    }

    public void method2() {
        readWriteLock.readLock().lock();
        System.out.println(Thread.currentThread().getName() + "\t 拿到读锁了，进来了...");
        readWriteLock.readLock().unlock();
        System.out.println(Thread.currentThread().getName() + "\t 运行结束");
    }
}
