package com.codecool.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* columns in DB:
        id
        degree_name
        min_coolcoins
*/
public class DegreeDAO extends CommonDAO {

    private Connection connection;

    public DegreeDAO(Connection connection) {
        this.connection = connection;
    }

    public int add(Degree degree) {
        
        String sqlString = "INSERT INTO degree (degree_name, min_earned_coolcoins) VALUES (?, ?);";
        return executeSQLUpdateDB(connection, sqlString, degree.toHashMap());
    }

    public int update(Degree degree) {

        String sqlString = "UPDATE degree SET degree_name = ?, min_earned_coolcoins = ? WHERE id = ?;";
        return executeSQLUpdateDB(connection, sqlString, degree.toHashMapWithId());
    }

    public Degree get(Integer id) {
        String sqlString = "SELECT * FROM degree WHERE id = ?;";
        
        Map<String, String> result = executeSQLSelect(connection, sqlString, id).get(0);

        //Degree(Integer id, String name, Integer minEarnedCoolcoins)
        return new Degree(Integer.valueOf(result.get("id")), 
                         result.get("degree_name"), 
                         Integer.valueOf(result.get("min_earned_coolcoins")));
    }

    public List<Degree> getList() {
        String sql = String.format("SELECT * FROM degree");

        List<Map<String, String>> results = executeSQLSelect(connection, sql);
        List<Degree> degrees = new ArrayList<Degree>();

        //Degree(Integer id, String name, Integer minEarnedCoolcoins)
        for (Map<String, String> result : results) {
            degrees.add(new Degree(Integer.valueOf(result.get("id")), 
                                 result.get("degree_name"), 
                                 Integer.valueOf(result.get("min_earned_coolcoins"))));
        }
        return degrees;
    }
}

