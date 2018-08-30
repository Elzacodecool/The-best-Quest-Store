package com.codecool.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


//@RunWith(MockitoJUnitRunner.class)

public class QuestDAOTest {

    @Mock
    private Connection connection;

    @Mock
    private Artifact artifact;

    @Mock
    private ResultSet resultSet;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSetMetaData metaData;



    private List<String> expectedQuestDetails;


    @BeforeEach
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);


    }

    @Test
    public void testAddToQuestDAOWhenNullPass() {
        QuestDAO questDao = new QuestDAO(connection);
        assertThrows(IllegalArgumentException.class, () -> {questDao.add(null); });
    }


    @Test
    public void testUpdateQuestDAOWhenNullPass() {
        QuestDAO questDao = new QuestDAO(connection);
        assertThrows(IllegalArgumentException.class, () -> {questDao.update(null); });
    }


    @Test
    public void testGetQuestById() throws Exception{
        Quest quest = new Quest(1, "quest", "new_quest", 10, "group");
        QuestDAO questDao = new QuestDAO(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.getMetaData()).thenReturn(metaData);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(metaData.getColumnCount()).thenReturn(5);
        when(metaData.getColumnName(1)).thenReturn("id");
        when(metaData.getColumnName(2)).thenReturn("quest_name");
        when(metaData.getColumnName(3)).thenReturn("quest_description");
        when(metaData.getColumnName(4)).thenReturn("prize");
        when(metaData.getColumnName(5)).thenReturn("category");
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("id")).thenReturn(Integer.toString(1));
        when(resultSet.getString("quest_name")).thenReturn("quest");
        when(resultSet.getString("quest_description")).thenReturn("new_quest");
        when(resultSet.getString("prize")).thenReturn(Integer.toString(10));
        when(resultSet.getString("category")).thenReturn("group");

        Integer actualDetails = questDao.get(1).getId();
        Integer expectedDetails = quest.getId();

        assertEquals(actualDetails, expectedDetails);

    }

    @Test
    public void testGetQuestHasProperDetails() throws SQLException {
        Quest quest = new Quest(1, "quest", "new_quest", 10, "group");
        QuestDAO questDao = new QuestDAO(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.getMetaData()).thenReturn(metaData);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(metaData.getColumnCount()).thenReturn(5);
        when(metaData.getColumnName(1)).thenReturn("id");
        when(metaData.getColumnName(2)).thenReturn("quest_name");
        when(metaData.getColumnName(3)).thenReturn("quest_description");
        when(metaData.getColumnName(4)).thenReturn("prize");
        when(metaData.getColumnName(5)).thenReturn("category");
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("id")).thenReturn(Integer.toString(1));
        when(resultSet.getString("quest_name")).thenReturn("quest");
        when(resultSet.getString("quest_description")).thenReturn("new_quest");
        when(resultSet.getString("prize")).thenReturn(Integer.toString(10));
        when(resultSet.getString("category")).thenReturn("group");

        ArrayList<String> actualDetails = getQuestDetails(questDao.get(quest.getId()));
        ArrayList<String> expectedDetails = getQuestDetails(quest);

        assertEquals(actualDetails, expectedDetails);
    }


    private ArrayList<String> getQuestDetails(Quest quest) {
        ArrayList<String> questDetailsList = new ArrayList<>();

        questDetailsList.add(Integer.toString(quest.getId()));
        questDetailsList.add(quest.getName());
        questDetailsList.add(quest.getDescription());
        questDetailsList.add(Integer.toString(quest.getPrize()));
        questDetailsList.add(quest.getCategory());

        return questDetailsList;
    }



}