package com.yws.cas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {
    public static void main(String[] args) {
        AtomicReference<User> atomicReference = new AtomicReference<>();
        User z3 = new User("z3", 22);
        User li4 = new User("li4", 28);

        atomicReference.set(z3);

        System.out.println(atomicReference.compareAndSet(z3, li4) + "\t " + atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(z3, li4) + "\t " + atomicReference.get().toString());
    }


}

@Getter
@Setter
@AllArgsConstructor
@ToString
class User{
    String userName;
    int age;
}
