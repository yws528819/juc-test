package com.yws.tl;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 需求1：5个销售卖房子，集团高层只关心销售总量的准确统计数。
 *
 * 需求2：5个销售卖完随机数房子，各自独立销售额度，自己业绩按提成走，分灶吃饭，各个销售自己动手，丰衣足食
 */
public class ThreadLocalDemo {
    public static void main(String[] args) {
        House house = new House();
        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                int size = new Random().nextInt(5) + 1;
                //System.out.println(size);
                for (int j = 0; j < size; j++) {
                    house.saleHouse();
                    house.saleVolumnsByThreadLocal();
                }
                System.out.println(Thread.currentThread().getName() + "\t 号销售卖出" + house.threadLocal.get());
            }, String.valueOf(i)).start();
        }

        //暂停几毫秒线程
        try{ TimeUnit.MICROSECONDS.sleep(300); }catch (Exception e) { e.printStackTrace(); }

        System.out.println(Thread.currentThread().getName() + "\t 共计卖出多少套：" + house.saleCount);
    }
}

class House {
    int saleCount = 0;
    public synchronized void saleHouse() {
        ++ saleCount;
    }


    //ThreadLocal<Integer> threadLocal = new ThreadLocal<>(){
    //    @Override
    //    protected Object initialValue() {
    //        return 0;
    //    }
    //};
    ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);
    public void saleVolumnsByThreadLocal() {
        threadLocal.set(1 + threadLocal.get());
    }
}
