package com.codecool.model;

import java.util.HashMap;
import java.util.Map;

public class Classroom {

    private Integer id;
    private String name;

    private Integer amountOfMentors;
    private Integer amountOfCodecoolers;

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

    public Integer getAmountOfMentors() {
        return this.amountOfMentors;
    }

    public void setAmountOfMentors(Integer amountOfMentors) {
        this.amountOfMentors = amountOfMentors;
    }

    public Integer getAmountOfCodecoolers() {
        return this.amountOfCodecoolers;
    }

    public void setAmountOfCodecoolers(Integer amountOfCodecoolers) {
        this.amountOfCodecoolers = amountOfCodecoolers;
    }

    public String toString() {
        return String.format("[Classroom] id: %d, name: %s, amountOfMentors: %d, amountOfCodecoolers: %d",
                                this.id, this.name, this.amountOfMentors, this.amountOfCodecoolers);
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