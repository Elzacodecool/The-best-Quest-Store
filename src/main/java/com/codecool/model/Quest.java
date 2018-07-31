package com.codecool.model;

public class Quest {

    private int id;
    private String name;
    private String description;
    private int prize;
    private String category;

    public Quest(String name, String description, int prize) {
        this.name = name;
        this.description = description;
        this.prize = prize;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrize() {
        return this.prize;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}