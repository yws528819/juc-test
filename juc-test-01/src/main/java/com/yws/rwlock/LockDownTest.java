package com.yws.rwlock;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockDownTest {
    public static void main(String[] args) {
        A a = new A();
        new Thread(() -> {
            a.method1(10);
        }, "t1").start();

        //暂停几秒线程
        try{ TimeUnit.SECONDS.sleep(5); }catch (Exception e) { e.printStackTrace(); }
        System.out.println("------------------5s-------------------");
        new Thread(() -> {
            a.method1(20);
        }, "t2").start();
    }



}


class A {
    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    public volatile int a = 0;

    public void method1(int val){
        readWriteLock.readLock().lock();
        System.out.println(Thread.currentThread().getName() + "---开头的读锁里进来了。。。");
        readWriteLock.readLock().unlock();

        readWriteLock.writeLock().lock();
        System.out.println(Thread.currentThread().getName() + "---进来了。。。");
        a = val;
        readWriteLock.readLock().lock();
        System.out.println(a);

        readWriteLock.writeLock().unlock();
        //暂停几秒线程
        System.out.println(Thread.currentThread().getName() + "---（写锁释放了才有效果）理论上锁已经降级了。。。。暂停10s");
        try{ TimeUnit.SECONDS.sleep(10); }catch (Exception e) { e.printStackTrace(); }
        System.out.println(Thread.currentThread().getName() + "---睡眠完毕");

        readWriteLock.readLock().unlock();

        System.out.println(Thread.currentThread().getName() + "---运行完毕");
    }
}
