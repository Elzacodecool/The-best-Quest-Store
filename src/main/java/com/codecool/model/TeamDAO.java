package com.codecool.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* columns in DB in table team:
        id
        team_name
        artifact_id
        leader_id
        status

   columns in DB in table codecooler_team:
        codecooler_id
        team_id
        coolcoins
*/
public class TeamDAO extends CommonDAO {

    private Connection connection;
    private ArtifactDAO artifactDAO;
    private CodecoolerDAO codecoolerDAO;

    public TeamDAO(Connection connection) {
        this.connection = connection;
        artifactDAO = new ArtifactDAO(connection);
        codecoolerDAO = new CodecoolerDAO(connection);
    }

    public int add(Team team) {
        
        String sqlString = "INSERT INTO team (team_name, artifact_id, leader_id, status) VALUES (?, ?, ?, ?);";
        return executeSQLUpdateDB(connection, sqlString, team.toHashMap());
    }

    public int update(Team team) {

        String sqlString = "UPDATE team SET team_name = ?, artifact_id = ?, leader_id = ?, status = ? WHERE id = ?;";
        return executeSQLUpdateDB(connection, sqlString, team.toHashMapWithId());
    }

    public Team get(Integer id) {
        String sqlString = "SELECT * FROM team WHERE id = ?;";
        
        Map<String, String> result = executeSQLSelect(connection, sqlString, id).get(0);

        Artifact artifact = artifactDAO.get(Integer.valueOf(result.get("artifact_id")));
        Codecooler leader = codecoolerDAO.get(Integer.valueOf(result.get("leader_id")));

        //Team(Integer id, String name, Artifact artifact, Codecooler leader, String status)
        return new Team(Integer.valueOf(result.get("id")), result.get("team_name"), 
                        artifact, leader, result.get("status"));
    }

    public List<Team> getList() {
        String sql = String.format("SELECT * FROM team");

        List<Map<String, String>> results = executeSQLSelect(connection, sql);
        List<Team> teams = new ArrayList<Team>();

        Artifact artifact;
        Codecooler leader;

        //Team(Integer id, String name, Artifact artifact, Codecooler leader, String status)
        for (Map<String, String> result : results) {

            artifact = artifactDAO.get(Integer.valueOf(result.get("artifact_id")));
            leader = codecoolerDAO.get(Integer.valueOf(result.get("leader_id")));

            teams.add(new Team(Integer.valueOf(result.get("id")), result.get("team_name"), 
                                artifact, leader, result.get("status")));
        }
        return teams;
    }
}


// implement import from DB members of team

