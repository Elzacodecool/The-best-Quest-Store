package com.codecool.model;

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
}
