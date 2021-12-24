package com.example.mongopractice.model;

import lombok.Data;

@Data
public class Person {
    private String id;
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age= age;
    }
}