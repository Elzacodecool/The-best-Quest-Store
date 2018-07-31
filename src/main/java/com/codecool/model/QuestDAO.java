package com.codecool.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* columns in DB:
        id
        quest_name
        quest_description
        prize
        category
*/
public class QuestDAO extends CommonDAO {

    private Connection connection;

    public QuestDAO(Connection connection) {
        this.connection = connection;
    }

    public int add(Quest quest) {
        
        String sqlString = "INSERT INTO quest (quest_name, quest_description, prize, category) VALUES (?, ?, ?, ?);";
        return executeSQLUpdateDB(connection, sqlString, quest.toHashMap());
    }

    public int update(Quest quest) {

        String sqlString = "UPDATE quest SET quest_name = ?, quest_description = ?, prize = ?, category = ? WHERE id = ?;";
        return executeSQLUpdateDB(connection, sqlString, quest.toHashMapWithId());
    }

    public Quest get(Integer id) {
        String sqlString = "SELECT * FROM quest WHERE id = ?;";
        
        Map<String, String> result = executeSQLSelect(connection, sqlString, id).get(0);

        //Quest(int id, String name, String description, int prize, String category)
        return new Quest(Integer.valueOf(result.get("id")), 
                         result.get("quest_name"), 
                         result.get("quest_description"), 
                         Integer.valueOf(result.get("prize")), 
                         result.get("category"));
    }

    public List<Quest> getList() {
        String sql = String.format("SELECT * FROM quest");

        List<Map<String, String>> results = executeSQLSelect(connection, sql);
        List<Quest> quests = new ArrayList<Quest>();

        //Quest(int id, String name, String description, int prize, String category)
        for (Map<String, String> result : results) {
            quests.add(new Quest(Integer.valueOf(result.get("id")), 
                                 result.get("quest_name"), 
                                 result.get("quest_description"), 
                                 Integer.valueOf(result.get("prize")), 
                                 result.get("category")));
        }
        return quests;
    }
}

