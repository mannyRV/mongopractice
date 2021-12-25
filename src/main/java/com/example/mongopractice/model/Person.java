package com.example.mongopractice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@NoArgsConstructor
public class Person {
    @MongoId
    private String id;
    private String name;
    private int age;
    private double assets;
    private List<Child> children;

    public Person(String id, String name, int age, double assets) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.assets = assets;
    }
}
