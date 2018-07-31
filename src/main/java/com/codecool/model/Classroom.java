package com.codecool.model;

import java.util.HashMap;
import java.util.Map;

public class Classroom {

    private Integer id;
    private String name;

    public Classroom(String name) {
        this.name = name;
    }

    // data from DB
    public Classroom(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    public String toString() {
        return String.format("[Classroom] id: %d, name: %s", this.id, this.name);
    }

    // for sql query to insert classroom to DB
    public Map<Integer, Object> toHashMap() {

        Map<Integer, Object> classroomMap = new HashMap<>();
        classroomMap.put(1, this.name);

        return classroomMap;
    }

    // for sql query to update classroom in DB
    public Map<Integer, Object> toHashMapWithId() {

        Map<Integer, Object> classroomMap = toHashMap();
        classroomMap.put(2, this.id);

        return classroomMap;
    }
}