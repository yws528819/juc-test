package com.yws.volatiles;

import static java.util.concurrent.TimeUnit.SECONDS;

public class VolatileSeeDemo {

    //static boolean flag = true;
    static volatile boolean flag = true;

    public static void main(String[] args) {

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ----come in");
            while (flag) {

            }
            System.out.println(Thread.currentThread().getName() + "\t ----flag被设置为false，程序停止");
        }, "t1").start();

        //暂停几秒钟
        try { SECONDS.sleep(1); } catch (Exception e){ e.printStackTrace(); }

        new Thread(() -> {
            flag = false;
            System.out.println(Thread.currentThread().getName() + "\t 修改完成flag：" + flag);
        }, "t2").start();


    }
}
