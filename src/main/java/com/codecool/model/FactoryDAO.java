package com.codecool.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FactoryDAO {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/";
    private static final String DB_NAME = "qq";
    private static final String userName = "adam";
    private static final String password = "adam12";


    public Connection createConnection() {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(DB_URL + DB_NAME, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } catch (ClassNotFoundException e) {
            e.getStackTrace();
        }
        return c;
    }

    public AppUserDAO getAppUserDAO() {
        return new AppUserDAO(createConnection());
    }

    public ArtifactDAO getArtifactDAO() {
        return new ArtifactDAO(createConnection());
    }

    public ClassroomDAO getClassroomDAO() {
        return new ClassroomDAO(createConnection());
    }

    public CodecoolerDAO getCodecoolerDAO() {
        return new CodecoolerDAO(createConnection());
    }

    public DegreeDAO getDegreeDAO() {
        return new DegreeDAO(createConnection());
    }

    public MentorDAO getMentorDAO() {
        return new MentorDAO(createConnection());
    }

    public QuestDAO getQuestDAO() {
        return new QuestDAO(createConnection());
    }

    public TeamDAO getTeamDAO() {
        return new TeamDAO(createConnection());
    }

}
