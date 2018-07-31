package com.codecool.model;

public class Classroom {

    private int id;
    private String name;

    public Classroom(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}