package com.codecool.model;

public class Degree {

    private int id;
    private String name;
    private int minEarnedCoolcoins;

    public Degree(String name, int minEarnedCoolcoins) {
        this.name = name;
        this.minEarnedCoolcoins = minEarnedCoolcoins;
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

    public int getMinEarnedCoolcoins() {
        return this.minEarnedCoolcoins;
    }

    public void setMinEarnedCoolcoins(int minEarnedCoolcoins) {
        this.minEarnedCoolcoins = minEarnedCoolcoins;
    }
}
