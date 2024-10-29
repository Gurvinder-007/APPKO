package com.example.appk;

public class Student {
    private int id;
    private String name;

    public Student(String id, String name) {
        this.id = Integer.parseInt(id);
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

