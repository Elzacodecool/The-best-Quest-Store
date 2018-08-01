package com.codecool.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* columns in DB:
        id
        classroom_name
*/
public class ClassroomDAO extends CommonDAO {

    private Connection connection;

    public ClassroomDAO(Connection connection) {
        this.connection = connection;
    }

    public int add(Classroom classroom) {
        
        String sqlString = "INSERT INTO classroom (classroom_name) VALUES (?);";
        return executeSQLUpdateDB(connection, sqlString, classroom.toHashMap());
    }

    public int update(Classroom classroom) {

        String sqlString = "UPDATE classroom SET classroom_name = ? WHERE id = ?;";
        return executeSQLUpdateDB(connection, sqlString, classroom.toHashMapWithId());
    }

    public Classroom get(Integer id) {
        String sqlString = "SELECT * FROM classroom WHERE id = ?;";
        
        Map<String, String> result = executeSQLSelect(connection, sqlString, id).get(0);

        //Classroom(Integer id, String name)
        return new Classroom(Integer.valueOf(result.get("id")), result.get("classroom_name"));
    }

    public List<Classroom> getList() {
        String sql = String.format("SELECT * FROM classroom");

        List<Map<String, String>> results = executeSQLSelect(connection, sql);
        List<Classroom> classrooms = new ArrayList<Classroom>();

        //Classroom(Integer id, String name)
        for (Map<String, String> result : results) {
            classrooms.add(new Classroom(Integer.valueOf(result.get("id")), result.get("classroom_name")));
        }
        return classrooms;
    }

    public int countAssignedMentors(Integer id) {
//TO DO ??
        return 1;
    }
}

