package com.codecool.model;

import java.util.ArrayList;
import java.util.List;

public class Mentor extends AppUser {

    private int id;
    private List<Classroom> classroomList;

    public Mentor(String login, String password, String firstName, String lastName, String email, String appuserType) {
        super(login, password, firstName, lastName, email, appuserType);
        this.classroomList = new ArrayList<Classroom>();
    }

    public int getId() {
        return this.id;
    }

    public List<Classroom> getClassroomList() {
        return this.classroomList;
    }
} 
