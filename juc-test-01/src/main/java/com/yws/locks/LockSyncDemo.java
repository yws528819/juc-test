package com.yws.locks;

public class LockSyncDemo {

    Object object = new Object();

    public  void m1() {
        synchronized (object) {
            System.out.println("---hello synchronized code block");
        }
    }

    public static void main(String[] args) {

    }
}
