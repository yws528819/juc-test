package com.yws.synupd;

/**
 * 这个锁对象并没有被共用扩散到其他线程使用，
 * 极端的说就是根本没有加这个锁对象的底层机器码，消除了锁的使用
 */
public class LockClearUpdDemo {
    static Object objectLock = new Object();

    public void m1() {
        //锁消除问题，JIT编译器会无视它，synchronized(o)，每次new出来的
        Object o = new Object();
        synchronized (o) {
            System.out.println("----hello LockClearUpdDemo \t " + o.hashCode() + "\t " + objectLock.hashCode());
        }
    }

    public static void main(String[] args) {
        LockClearUpdDemo lockClearUpdDemo = new LockClearUpdDemo();
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                lockClearUpdDemo.m1();
            }, String.valueOf(i)).start();
        }
    }

}
