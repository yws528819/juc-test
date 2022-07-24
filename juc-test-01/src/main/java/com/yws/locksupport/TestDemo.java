package com.yws.locksupport;

import java.util.concurrent.TimeUnit;

public class TestDemo {

    private boolean value = true;

    public boolean getValue() {
        return value;
    }

    public void setValue() {
        value = false;
    }

    public static void main(String[] args) {
        TestDemo test = new TestDemo();
        new Thread(() -> {
            //暂停几秒钟
            try { TimeUnit.SECONDS.sleep(1); } catch (Exception e){ e.printStackTrace(); }
            //暂停几秒钟
            test.setValue();

        }, "t1").start();

        new Thread(() -> {
            while (test.value) {

            }
            System.out.println(test.getValue());
        }, "t2").start();
    }
}
