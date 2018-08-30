package com.codecool.model;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppUserDAOTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private ResultSetMetaData metaData;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAppUserByLogin() throws SQLException {
        AppUserDAO appUserDAO = new AppUserDAO(connection);
        String user = "user";
        AppUser appUser = new AppUser(user, user, user, user, user, "mentor");
        List<String> appUserData = new ArrayList<>();
        appUserData.add(appUser.login);
        appUserData.add(appUser.firstName);
        appUserData.add(appUser.lastName);


        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.getMetaData()).thenReturn(metaData);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(metaData.getColumnCount()).thenReturn(6);
        when(metaData.getColumnName(1)).thenReturn("login");
        when(metaData.getColumnName(2)).thenReturn("password");
        when(metaData.getColumnName(3)).thenReturn("first_name");
        when(metaData.getColumnName(4)).thenReturn("last_name");
        when(metaData.getColumnName(5)).thenReturn("email");
        when(metaData.getColumnName(6)).thenReturn("appuser_type");
        when(resultSet.getString("login")).thenReturn(user);
        when(resultSet.getString("password")).thenReturn(user);
        when(resultSet.getString("first_name")).thenReturn(user);
        when(resultSet.getString("last_name")).thenReturn(user);
        when(resultSet.getString("email")).thenReturn(user);
        when(resultSet.getString("appuser_type")).thenReturn("mentor");


        AppUser recievedUser = appUserDAO.get("user");
        List<String> recievedAppUserData = new ArrayList<>();
        recievedAppUserData.add(recievedUser.getLogin());
        recievedAppUserData.add(recievedUser.getFirstName());
        recievedAppUserData.add(recievedUser.getLastName());

        assertEquals(appUserData,recievedAppUserData);

    }

}