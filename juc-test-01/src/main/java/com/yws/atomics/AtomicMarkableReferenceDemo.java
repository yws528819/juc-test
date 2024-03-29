package com.yws.atomics;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class AtomicMarkableReferenceDemo {
    static AtomicMarkableReference markableReference = new AtomicMarkableReference(100, false);

    public static void main(String[] args) {
        new Thread(() -> {
            boolean marked = markableReference.isMarked();
            System.out.println(Thread.currentThread().getName() + "\t 默认标识：" + marked);

            //暂停几秒线程
            try{ TimeUnit.SECONDS.sleep(1); }catch (Exception e) { e.printStackTrace(); }

            markableReference.compareAndSet(100, 1000, marked, !marked);

        }, "t1").start();


        new Thread(() -> {
            boolean marked = markableReference.isMarked();
            System.out.println(Thread.currentThread().getName() + "\t 默认标识：" + marked);

            //暂停几秒线程
            try{ TimeUnit.SECONDS.sleep(2); }catch (Exception e) { e.printStackTrace(); }

            boolean b = markableReference.compareAndSet(100, 2000, marked, !marked);
            System.out.println(Thread.currentThread().getName() + "\t" + "t2线程CASresult：" + b);
            System.out.println(Thread.currentThread().getName() + "\t" + markableReference.isMarked());
            System.out.println(Thread.currentThread().getName() + "\t" + markableReference.getReference());
        }, "t2").start();
    }
}
