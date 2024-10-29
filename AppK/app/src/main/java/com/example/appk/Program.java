package com.example.appk;

public class Program {
    private int id;
    private String name;
    private String description;

    public Program(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name; // This is what will be displayed in the ListView
    }
}


