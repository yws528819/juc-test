package com.yws.atomics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 以一种线程安全的方式来操作非线程安全对象的某些字段
 *
 * 需求；
 * 10个线程
 * 每一线程转账1000
 * 不使用Synchronized，尝试使用AtomicIntegerFieldUpdater来实现。
 */
public class AtomicIntegerFieldUpdaterDemo {

    public static void main(String[] args) throws InterruptedException {
        BankAccount bankAccount = new BankAccount();
        CountDownLatch countDownLatch = new CountDownLatch(10);

        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    //bankAccount.add();
                    bankAccount.transfer(bankAccount);
                }
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        countDownLatch.await();

        System.out.println(Thread.currentThread().getName() + "\t result:" + bankAccount.money);
    }
}


class BankAccount{
    String bankName = "CCB";

    //使用public volatile修饰符
    volatile int money = 0;//钱

    public void add() {
        money ++;
    }

    //因为对象的属性修改类型原子类都是抽象类，所以每次使用都必须使用静态方法newUpdater()创建一个更新器，
    //并且需要设置想要更新的类和属性。
    AtomicIntegerFieldUpdater<BankAccount> fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(BankAccount.class, "money");

    public void transfer(BankAccount bankAccount) {
        fieldUpdater.getAndIncrement(bankAccount);
    }


}
