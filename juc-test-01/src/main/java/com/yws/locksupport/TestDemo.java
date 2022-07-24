package com.yws.locksupport;

import java.util.concurrent.TimeUnit;

public class TestDemo {

    private int value = 0;

    public int getValue() {
        return value;
    }

    public void setValue() {
        ++ value;
    }

    public static void main(String[] args) {
        TestDemo test = new TestDemo();
        new Thread(() -> {
            test.setValue();
            //暂停几秒钟
            try { TimeUnit.SECONDS.sleep(1); } catch (Exception e){ e.printStackTrace(); }
        }, "t1").start();

        new Thread(() -> {
            while (test.getValue() == 0) {

            }
            System.out.println(test.getValue());
        }, "t2").start();
    }
}
