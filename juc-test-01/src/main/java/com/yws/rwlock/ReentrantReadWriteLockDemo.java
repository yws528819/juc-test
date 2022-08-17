package com.yws.rwlock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class ReentrantReadWriteLockDemo {
    public static void main(String[] args) {
        MyResource myResource = new MyResource();
        for (int i = 1; i <= 10; i++) {
            final int finalI = i;
            new Thread(() -> {
                myResource.write(finalI + "", finalI + "");
            }, String.valueOf(i)).start();
        }
        for (int i = 1; i <= 10; i++) {
            final int finalI = i;
            new Thread(() -> {
                myResource.read(finalI + "");
            }, String.valueOf(i)).start();
        }
    }
}

//资源类，模拟一个简单的缓存
class MyResource{
    Map<String, String> map = new HashMap<>();

    Lock lock = new ReentrantLock();
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void write(String key, String val) {
        readWriteLock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t 正在写入");
            map.put(key, val);
            //暂停几毫秒
            try { MILLISECONDS.sleep(500); } catch (Exception e){ e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName() + "\t 完成写入");
        }finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void read(String key) {
        readWriteLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t 正在读取");
            String result = map.get(key);
            //暂停几毫秒
            try { TimeUnit.MILLISECONDS.sleep(200); } catch (Exception e){ e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName() + "\t 完成读取\t" + result);
        }finally {
            readWriteLock.readLock().unlock();
        }
    }
}
