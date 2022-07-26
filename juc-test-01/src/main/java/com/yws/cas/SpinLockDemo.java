package com.yws.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * 题目：实现一个自旋锁，复习CAS思想
 * 自旋锁好处：循环比较获取没有类似wait的阻塞。
 *
 * 通过CAS操作实现自旋锁，A线程进来先调用myLock方法自己持有锁5s，
 * B随后进来发现有线程持有锁，所以只能通过自旋等待，直到A释放锁后B随后抢到。
 */
public class SpinLockDemo {

    AtomicReference<Thread> atomicReference =  new AtomicReference<>();

    public void lock() {
        System.out.println(Thread.currentThread().getName() + "\t ---come in");
        while (!atomicReference.compareAndSet(null, Thread.currentThread())) {

        }
    }

    public void unLock() {
        atomicReference.compareAndSet(Thread.currentThread(), null);
        System.out.println(Thread.currentThread().getName() + "\t -----task over, unlock...");
    }


    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(() ->  {
            spinLockDemo.lock();

            //暂停几秒钟
            try { TimeUnit.SECONDS.sleep(5); } catch (Exception e){ e.printStackTrace(); }

            spinLockDemo.unLock();
        }, "t1").start();

        //暂停几毫秒
        try { MILLISECONDS.sleep(500); } catch (Exception e){ e.printStackTrace(); }

        new Thread(() ->  {
            spinLockDemo.lock();

            spinLockDemo.unLock();
        }, "t2").start();
    }
}
