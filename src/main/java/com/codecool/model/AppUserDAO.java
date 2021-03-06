package com.codecool.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* columns in DB:
        login
        password
        first_name
        last_name
        email
        appuser_type
*/
public class AppUserDAO extends CommonDAO {

    private Connection connection;

    public AppUserDAO(Connection connection) {
        this.connection = connection;
    }

// when login is in DB, where check that? in controller?
    public int add(AppUser appUser) {
        
        String sqlString = "INSERT INTO appuser (login, password, first_name, last_name, email, appuser_type) VALUES (?, ?, ?, ?, ?, ?);";
        return executeSQLUpdateDB(connection, sqlString, appUser.toHashMapAppUser());
    }

    public int update(AppUser appUser) {

        String sqlString = "UPDATE appuser SET password = ?, first_name = ?, last_name = ?, email = ? WHERE login = ?;";
        return executeSQLUpdateDB(connection, sqlString, appUser.toHashMapAppUserToUpdate());
    }

    public AppUser get(String login) {
        String sqlString = "SELECT * FROM appuser WHERE login = ?;";
        
        List<Map<String, String>> resultList = executeSQLSelect(connection, sqlString, login);
        Map<String, String> result;

        if (resultList.size() > 0) {
            result = resultList.get(0);
        } else {
            return null;
        }

        //AppUser(String login, String password, String firstName, String lastName, String email, String appuserType)
        return new AppUser(result.get("login"), 
                            result.get("password"), 
                            result.get("first_name"), 
                            result.get("last_name"),
                            result.get("email"), 
                            result.get("appuser_type"));
    }

    public List<AppUser> getList() {
        String sqlString = "SELECT * FROM appuser";

        List<Map<String, String>> results = executeSQLSelect(connection, sqlString);
        List<AppUser> appUsers = new ArrayList<AppUser>();

        //AppUser(String login, String password, String firstName, String lastName, String email, String appuserType)
        for (Map<String, String> result : results) {
            appUsers.add(new AppUser(result.get("login"), 
                                    result.get("password"), 
                                    result.get("first_name"), 
                                    result.get("last_name"),
                                    result.get("email"), 
                                    result.get("appuser_type")));
        }
        return appUsers;
    }
}

