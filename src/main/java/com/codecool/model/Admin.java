package com.codecool.model;

public class Admin extends AppUser {

    private int id;

    public Admin(String login, String password, String firstName, String lastName, String email, String appuserType) {
        super(login, password, firstName, lastName, email, appuserType);
    }

    public int getId() {
        return this.id;
    }
} 
