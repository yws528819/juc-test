package com.yws.objecthead;

import org.openjdk.jol.info.ClassLayout;

public class JOLDemo {
    public static void main(String[] args) {
        // System.out.println(VM.current().details());
        // System.out.println(VM.current().objectAlignment());

        // Object o = new Object();
        // System.out.println(ClassLayout.parseInstance(o).toPrintable());

        Customer customer = new Customer();
        System.out.println(ClassLayout.parseInstance(customer).toPrintable());
    }
}

class Customer{
    int id;
    boolean flag = false;
}
