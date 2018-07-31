package com.codecool.model;

import java.util.HashMap;
import java.util.Map;

public class Artifact {

    private Integer id;
    private String name;
    private String description;
    private Integer cost;
    private String category;

    public Artifact(String name, String description, Integer cost, String category) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.category = category;
    }

    // data from DB
    public Artifact(Integer id, String name, String description, Integer cost, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.category = category;
    }

    public Integer getId() {
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

    public Integer getCost() {
        return this.cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String toString() {
        return String.format("[Artifact] id: %d, name: %s, description: %s, cost: %d, category: %s", 
                            this.id, this.name, this.description, this.cost, this.category);
    }

    // for sql query to insert quest to DB
    public Map<Integer, Object> toHashMap() {

        Map<Integer, Object> questMap = new HashMap<>();
        questMap.put(1, this.name);
        questMap.put(2, this.description);
        questMap.put(3, this.cost);
        questMap.put(4, this.category);

        return questMap;
    }

    // for sql query to update quest in DB
    public Map<Integer, Object> toHashMapWithId() {

        Map<Integer, Object> questMap = toHashMap();
        questMap.put(5, this.id);

        return questMap;
    }
}