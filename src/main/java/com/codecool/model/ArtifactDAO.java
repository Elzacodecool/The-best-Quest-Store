package com.codecool.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
    
/* columns in DB:
        id
        artifact_name
        artifact_description
        cost
        category
*/
public class ArtifactDAO extends CommonDAO {

    private Connection connection;

    public ArtifactDAO(Connection connection) {
        this.connection = connection;
    }

    public int add(Artifact artifact) {
        
        String sqlString = "INSERT INTO artifact (artifact_name, artifact_description, cost, category) VALUES (?, ?, ?, ?);";
        return executeSQLUpdateDB(connection, sqlString, artifact.toHashMap());
    }

    public int update(Artifact artifact) {

        String sqlString = "UPDATE artifact SET artifact_name = ?, artifact_description = ?, cost = ?, category = ? WHERE id = ?;";
        return executeSQLUpdateDB(connection, sqlString, artifact.toHashMapWithId());
    }

    public Artifact get(Integer id) {
        String sqlString = "SELECT * FROM artifact WHERE id = ?;";
        
        Map<String, String> result = executeSQLSelect(connection, sqlString, id).get(0);

        //Artifact(int id, String name, String description, int cost, String category)
        return new Artifact(Integer.valueOf(result.get("id")), 
                         result.get("artifact_name"), 
                         result.get("artifact_description"), 
                         Integer.valueOf(result.get("cost")), 
                         result.get("category"));
    }

    public List<Artifact> getList() {
        String sql = String.format("SELECT * FROM artifact");

        List<Map<String, String>> results = executeSQLSelect(connection, sql);
        List<Artifact> artifacts = new ArrayList<Artifact>();

        //Artifact(int id, String name, String description, int cost, String category)
        for (Map<String, String> result : results) {
            artifacts.add(new Artifact(Integer.valueOf(result.get("id")), 
                                 result.get("artifact_name"), 
                                 result.get("artifact_description"), 
                                 Integer.valueOf(result.get("cost")), 
                                 result.get("category")));
        }
        return artifacts;
    }
}