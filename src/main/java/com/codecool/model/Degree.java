package com.codecool.model;

import java.util.HashMap;
import java.util.Map;

public class Degree {

    private Integer id;
    private String name;
    private Integer minEarnedCoolcoins;

    public Degree(String name, Integer minEarnedCoolcoins) {
        this.name = name;
        this.minEarnedCoolcoins = minEarnedCoolcoins;
    }

    // data from DB
    public Degree(Integer id, String name, Integer minEarnedCoolcoins) {
        this.id = id;
        this.name = name;
        this.minEarnedCoolcoins = minEarnedCoolcoins;
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

    public Integer getMinEarnedCoolcoins() {
        return this.minEarnedCoolcoins;
    }

    public void setMinEarnedCoolcoins(Integer minEarnedCoolcoins) {
        this.minEarnedCoolcoins = minEarnedCoolcoins;
    }

    public String toString() {
        return String.format("[Degree] id: %d, name: %s, minEarnedCoolcoins: %d", 
                            this.id, this.name, this.minEarnedCoolcoins);
    }

    // for sql query to insert degree to DB
    public Map<Integer, Object> toHashMap() {

        Map<Integer, Object> degreeMap = new HashMap<>();
        degreeMap.put(1, this.name);
        degreeMap.put(2, this.minEarnedCoolcoins);

        return degreeMap;
    }

    // for sql query to update degree in DB
    public Map<Integer, Object> toHashMapWithId() {

        Map<Integer, Object> degreeMap = toHashMap();
        degreeMap.put(3, this.id);

        return degreeMap;
    }
}
