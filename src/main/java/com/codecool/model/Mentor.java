package com.codecool.model;

import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mentor extends AppUser {

    private Integer id;
    private List<Classroom> classroomList;

    public Mentor(String login, String password, String firstName, String lastName, String email, String appuserType) {
        super(login, password, firstName, lastName, email, appuserType);
        this.classroomList = new ArrayList<Classroom>();
    }

    // data from DB
    public Mentor(Integer id, AppUser appUser) {
        super(appUser.getLogin(), 
                appUser.getPassword(), 
                appUser.getFirstName(), 
                appUser.getLastName(), 
                appUser.getEmail(), 
                appUser.getAppuserType());
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public List<Classroom> getClassroomList() {
        return this.classroomList;
    }

    public void setClassroomList(List<Classroom> classroomList) {
        this.classroomList = classroomList;
    }

    public void addClassroom(Classroom classroom) {
        this.classroomList.add(classroom);
    }

    public String toString() {

        StringBuilder classrooms = null;

        if (this.classroomList != null) {
            classrooms = new StringBuilder();
            for (Classroom classroom : this.classroomList) {
                classrooms.append("\n");
                classrooms.append(classroom.toString());
                classrooms.append(", ");
            }
        }

        return String.format("[Mentor] login: %s, id: %d, \nclassrooms: %s", 
                            this.login, this.id, classrooms);
    }

    // for sql query to insert mentor to DB
    public Map<Integer, Object> toHashMapMentor() {

        Map<Integer, Object> mentorMap = new HashMap<>();
        mentorMap.put(1, this.login);
        return mentorMap;
    }
}
