package com.yws.volatiles;

import static java.util.concurrent.TimeUnit.SECONDS;

public class VolatileNoAtomicDemo {

    public static void main(String[] args) {
        MyNumber myNumber = new MyNumber();
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                for (int j=0; j<1000; j++) {
                    myNumber.addPlusPlus();
                }
            }, String.valueOf(i)).start();
        }

        //暂停几秒钟
        try { SECONDS.sleep(2); } catch (Exception e){ e.printStackTrace(); }

        System.out.println(myNumber.number);
    }
}



class MyNumber{
    volatile int number;

    public void addPlusPlus() {
        number ++;
    }
}
