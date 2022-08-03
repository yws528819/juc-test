package com.yws.tl;

public class ReferenceDemo {
    public static void main(String[] args) {

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
