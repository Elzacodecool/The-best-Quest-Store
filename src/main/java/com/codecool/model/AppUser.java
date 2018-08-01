package com.codecool.model;

import java.util.HashMap;
import java.util.Map;

public class AppUser {

    protected String login;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String appuserType;

    public AppUser(String login, String password, String firstName, String lastName, String email, String appuserType) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.appuserType = appuserType;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAppuserType() {
        return this.appuserType;
    }

    public String toString() {
        return String.format("[AppUser] login: %s, password: %s, firstName: %s, lastName: %s, email: %s, appuserType: %s", 
                                this.login, this.password, this.firstName, this.lastName, this.email, this.appuserType);
    }

    // for sql query to insert appUser to DB
    public Map<Integer, Object> toHashMap() {

        Map<Integer, Object> appUserMap = new HashMap<>();
        appUserMap.put(1, this.login);
        appUserMap.put(2, this.password);
        appUserMap.put(3, this.firstName);
        appUserMap.put(4, this.lastName);
        appUserMap.put(5, this.email);
        appUserMap.put(6, this.appuserType);

        return appUserMap;
    }

    // for sql query to insert appUser to DB
    public Map<Integer, Object> toHashMapToUpdate() {

        Map<Integer, Object> appUserMap = new HashMap<>();
        appUserMap.put(1, this.password);
        appUserMap.put(2, this.firstName);
        appUserMap.put(3, this.lastName);
        appUserMap.put(4, this.email);
        appUserMap.put(5, this.login);

        return appUserMap;
    }
}