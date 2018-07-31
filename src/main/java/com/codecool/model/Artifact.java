package com.codecool.model;

public class Artifact {

    private int id;
    private String name;
    private String description;
    private int cost;
    private String category;

    public Artifact(String name, String description, int cost) {
        this.name = name;
        this.description = description;
        this.cost = cost;
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

    public int getCost() {
        return this.cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}