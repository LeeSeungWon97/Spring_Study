package com.hello.core;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HelloLombok {
    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();
        helloLombok.setName("name");
        helloLombok.setAge(20);
        String name = helloLombok.getName();
        int age = helloLombok.getAge();
        System.out.println("name = " + name);
        System.out.println("age = " + age);
    }
}
