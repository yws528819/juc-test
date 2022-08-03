package com.yws.tl;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class ReferenceDemo {
    public static void main(String[] args) {
        MyObject myObject = new MyObject();
        ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();
        PhantomReference<MyObject> phantomReference = new PhantomReference<>(myObject, referenceQueue);

        List<Object> list = new ArrayList<>();

        new Thread(() -> {
            while (true) {
                list.add(new byte[2 * 1024 * 1024]);
                //暂停几毫秒
                try { MILLISECONDS.sleep(800); } catch (Exception e){ e.printStackTrace(); }
                System.out.println(phantomReference.get() + "\t list add ok");
            }

        }, "t1").start();

        new Thread(() -> {
            while (true) {
                Reference<? extends MyObject> reference = referenceQueue.poll();
                if (reference != null) {
                    System.out.println("---有虚对象回收加入了队列");
                    break;
                }
            }
        }, "t2").start();
    }

    private static void weakReference() {
        WeakReference<MyObject> weakReference = new WeakReference<>(new MyObject());

        System.out.println("------gc before" + weakReference.get());
        System.gc();
        System.out.println("------gc after" + weakReference.get());
    }

    private static void softReference() {
        SoftReference<MyObject> softReference = new SoftReference<>(new MyObject());

        System.gc();
        System.out.println("------gc after内存够用：" + softReference.get());

        try {
            byte[] bytes = new byte[20 * 1024 * 1024];
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            System.out.println("------gc after内存不够：" + softReference.get());
        }
    }

    private static void strongReference() {
        MyObject myObject = new MyObject();
        System.out.println("gc before:" + myObject);

        myObject = null;
        System.gc();//人工开启GC

        System.out.println("gc after:" + myObject);
    }
}

class MyObject{
    //这个方法一般不用复写
    @Override
    protected void finalize() throws Throwable {
        //finalize的通常目的是在对象不可撤销丢弃之前执行的清理操作。
        System.out.println("-------invoke finalize method~!!!");
    }
}
