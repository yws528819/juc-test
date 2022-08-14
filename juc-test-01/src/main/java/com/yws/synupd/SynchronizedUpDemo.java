package com.yws.synupd;

import org.openjdk.jol.info.ClassLayout;

import static java.util.concurrent.TimeUnit.SECONDS;

public class SynchronizedUpDemo {
    public static void main(String[] args) {
        // //暂停几秒钟
        // try { SECONDS.sleep(5); } catch (Exception e){ e.printStackTrace(); }
        //
        // Object o = new Object();
        // System.out.println("本应是偏向锁");
        // System.out.println(ClassLayout.parseInstance(o).toPrintable());
        //
        // o.hashCode();//当一个对象已经计算过identity hash code，它就无法进入偏向锁状态
        //
        // synchronized (o) {
        //     System.out.println("本应是偏向锁，但是由于计算过一次性hash，会直接升级为轻量级锁");
        //     System.out.println(ClassLayout.parseInstance(o).toPrintable());
        // }

        //睡眠5s，保证开启偏向锁
        try { SECONDS.sleep(5); } catch (Exception e){ e.printStackTrace(); }

        Object o = new Object();
        synchronized (o) {
            o.hashCode();
            System.out.println("偏向锁过程中遇到一致性哈希计算请求，立马撤销偏向模式，膨胀为重量级锁");
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }

    private static void biasedLock() {
        //暂停几秒钟（等JVM初始化，偏向锁开启完毕再new）
        try { SECONDS.sleep(5); } catch (Exception e){ e.printStackTrace(); }

        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        System.out.println("-------------------------");

        synchronized (o) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }

    private static void noLock() {
        Object o = new Object();

        o.hashCode();
        System.out.println("10进制：" + o.hashCode());
        System.out.println("16进制：" + Integer.toHexString(o.hashCode()));
        System.out.println("2进制：" + Integer.toBinaryString(o.hashCode()));

        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
