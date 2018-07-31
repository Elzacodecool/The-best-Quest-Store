package com.codecool.model;

import java.util.HashMap;
import java.util.Map;

public class Quest {

    private Integer id;
    private String name;
    private String description;
    private Integer prize;
    private String category;

    public Quest(String name, String description, Integer prize, String category) {
        this.name = name;
        this.description = description;
        this.prize = prize;
        this.category = category;
    }

    // data from DB
    public Quest(Integer id, String name, String description, Integer prize, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.prize = prize;
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

    public Integer getPrize() {
        return this.prize;
    }

    public void setPrize(Integer prize) {
        this.prize = prize;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String toString() {
        return String.format("[Quest] id: %s, name: %s, description: %s, prize: %d, category: %s", 
                            this.id, this.name, this.description, this.prize, this.category);
    }

    // for sql query to insert quest to DB
    public Map<Integer, Object> toHashMap() {

        Map<Integer, Object> questMap = new HashMap<>();
        questMap.put(1, this.name);
        questMap.put(2, this.description);
        questMap.put(3, this.prize);
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