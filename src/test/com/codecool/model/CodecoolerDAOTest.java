package com.codecool.model;


import com.sun.org.apache.bcel.internal.classfile.Code;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.omg.IOP.Codec;

import java.sql.*;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CodecoolerDAOTest {

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
    public void getCodecoolerByID() throws SQLException{
        ClassroomDAO classroomDAO = new ClassroomDAO(connection);
        CodecoolerDAO codecoolerDAO = new CodecoolerDAO(connection);
        AppUserDAO appUserDAO = new AppUserDAO(connection);
        String user = "user";

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.getMetaData()).thenReturn(metaData);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        AppUser appUser = new AppUser(user, user, user, user, user, "mentor");
        Classroom classroom = new Classroom(1, "brick");
        Codecooler codecooler = new Codecooler(1, appUser, classroom, 20, 1);




        when(metaData.getColumnCount()).thenReturn(5);
        when(metaData.getColumnName(1)).thenReturn("id");
        when(metaData.getColumnName(2)).thenReturn("appuser_login");
        when(metaData.getColumnName(3)).thenReturn("classroom_id");
        when(metaData.getColumnName(4)).thenReturn("earned_coolcoins");
        when(metaData.getColumnName(5)).thenReturn("wallet");

        when(resultSet.getString("id")).thenReturn(Integer.toString(1));
        when(resultSet.getString("appuser_login")).thenReturn("user");
        when(resultSet.getString("classroom_id")).thenReturn("1");
        when(resultSet.getString("earned_coolcoins")).thenReturn(Integer.toString(20));
        when(resultSet.getString("wallet")).thenReturn(Integer.toString(1));

        codecooler.getId()

        assertEquals();
    }

}