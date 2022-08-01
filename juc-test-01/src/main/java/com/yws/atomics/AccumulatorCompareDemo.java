package com.yws.atomics;

import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * 需求：50个线程，每个线程100w次，总点赞数出来
 */
public class AccumulatorCompareDemo {
    public static final int _1W = 10000;
    public static final int THREAD_NUMBER = 50;

    public static void main(String[] args) throws InterruptedException {
        ClickNumber clickNumber = new ClickNumber();

        CountDownLatch countDownLatch1 = new CountDownLatch(THREAD_NUMBER);
        CountDownLatch countDownLatch2 = new CountDownLatch(THREAD_NUMBER);
        CountDownLatch countDownLatch3 = new CountDownLatch(THREAD_NUMBER);
        CountDownLatch countDownLatch4 = new CountDownLatch(THREAD_NUMBER);

        //创建后立即start，常用
        StopWatch watch = StopWatch.createStarted();

        for (int i = 1; i <= THREAD_NUMBER; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100 * _1W; j++) {
                    clickNumber.clickBySynchronized();
                }
                countDownLatch1.countDown();
            }, String.valueOf(i)).start();
        }
        countDownLatch1.await();
        System.out.println("花费的时间>>" + watch.getTime() + "ms, clickBySynchronized: " + clickNumber.number);


        //复位后, 重新计时
        watch.reset();
        watch.start();

        for (int i = 1; i <= THREAD_NUMBER; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100 * _1W; j++) {
                    clickNumber.clickByAtomicLong();
                }
                countDownLatch2.countDown();
            }, String.valueOf(i)).start();
        }
        countDownLatch2.await();
        System.out.println("花费的时间>>" + watch.getTime() + "ms, clickByAtomicLong: " + clickNumber.atomicLong.get());



        //复位后, 重新计时
        watch.reset();
        watch.start();

        for (int i = 1; i <= THREAD_NUMBER; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100 * _1W; j++) {
                    clickNumber.clickByLongAdder();
                }
                countDownLatch3.countDown();
            }, String.valueOf(i)).start();
        }
        countDownLatch3.await();
        System.out.println("花费的时间>>" + watch.getTime() + "ms, clickByLongAdder: " + clickNumber.longAdder.sum());




        //复位后, 重新计时
        watch.reset();
        watch.start();

        for (int i = 1; i <= THREAD_NUMBER; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100 * _1W; j++) {
                    clickNumber.clickByLongAccumulator();
                }
                countDownLatch4.countDown();
            }, String.valueOf(i)).start();
        }
        countDownLatch4.await();
        System.out.println("花费的时间>>" + watch.getTime() + "ms, clickByLongAccumulator: " + clickNumber.longAccumulator.get());
    }
}


class ClickNumber {
    int number = 0;
    public synchronized void clickBySynchronized() {
        number ++;
    }

    AtomicLong atomicLong = new AtomicLong(0);
    public void clickByAtomicLong() {
        atomicLong.getAndIncrement();
    }

    LongAdder longAdder = new LongAdder();
    public void clickByLongAdder() {
        longAdder.increment();
    }

    LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);
    public void clickByLongAccumulator() {
        longAccumulator.accumulate(1);
    }
}
