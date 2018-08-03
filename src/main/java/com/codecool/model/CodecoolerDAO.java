package com.codecool.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/* columns in DB in table codecooler:
        id
        appuser_login
        classroom_id
        earned_coolcoins
        wallet

   columns in DB in table codecooler_artifact:
        id
        codecooler_id
        artifact_id,
        purchase_date
        use_date

   columns in DB in table codecooler_quest:
        id
        codecooler_id
        quest_id
        mark_date
*/
public class CodecoolerDAO extends CommonDAO {

    private Connection connection;
    private AppUserDAO appUserDAO;
    private ClassroomDAO classroomDAO;
    private ArtifactDAO artifactDAO;
    private QuestDAO questDAO;

    public CodecoolerDAO(Connection connection) {
        this.connection = connection;
        appUserDAO = new AppUserDAO(connection);
        classroomDAO = new ClassroomDAO(connection);
        artifactDAO = new ArtifactDAO(connection);
        questDAO = new QuestDAO(connection);
    }

    public int add(Codecooler codecooler) {
        
        appUserDAO.add(codecooler);

        String sqlString = "INSERT INTO codecooler (appuser_login, classroom_id, earned_coolcoins, wallet) VALUES (?, ?, ?, ?);";
        return executeSQLUpdateDB(connection, sqlString, codecooler.toHashMapCodecooler());
    }

    public int update(Codecooler codecooler) {

        appUserDAO.update(codecooler);

        String sqlString = "UPDATE codecooler SET classroom_id = ?, earned_coolcoins = ?, wallet = ? WHERE id = ?;";
        return executeSQLUpdateDB(connection, sqlString, codecooler.toHashMapCodecoolerToUpdate());
    }

    public Codecooler get(AppUser appUser) {
        String sqlString = "SELECT * FROM codecooler WHERE appuser_login = ?;";
        
        Map<String, String> result = executeSQLSelect(connection, sqlString, appUser.getLogin()).get(0);

        Integer id = Integer.valueOf(result.get("id"));
        Classroom classroom = classroomDAO.get(Integer.valueOf(result.get("classroom_id")));

        //Codecooler(Integer id, AppUser appUser, Classroom classroom, Integer earnedCoolcoins, Integer wallet)
        Codecooler codecooler = new Codecooler(id, appUser, classroom, 
                                Integer.valueOf(result.get("earned_coolcoins")), 
                                Integer.valueOf(result.get("wallet")));

        codecooler.setArtifactList(getCodecoolerArtifactList(id));  
        codecooler.setQuestList(getCodecoolerQuestList(id));             

        return codecooler;
    }

    public Codecooler get(Integer id) {
        String sqlString = "SELECT * FROM codecooler WHERE id = ?;";
        
        Map<String, String> result = executeSQLSelect(connection, sqlString, id).get(0);

        Classroom classroom = classroomDAO.get(Integer.valueOf(result.get("classroom_id")));
        AppUser appUser = appUserDAO.get(result.get("appuser_login"));

        //Codecooler(Integer id, AppUser appUser, Classroom classroom, Integer earnedCoolcoins, Integer wallet)
        Codecooler codecooler = new Codecooler(id, appUser, classroom, 
                                Integer.valueOf(result.get("earned_coolcoins")), 
                                Integer.valueOf(result.get("wallet")));
            
        codecooler.setArtifactList(getCodecoolerArtifactList(id)); 
        codecooler.setQuestList(getCodecoolerQuestList(id)); 

        return codecooler;
    }

    public List<CodecoolerArtifact> getCodecoolerArtifactList(Integer id) {
        String sqlString = "SELECT * FROM codecooler_artifact WHERE codecooler_id = ?;";
   
        List<Map<String, String>> results = executeSQLSelect(connection, sqlString, id);

        List<CodecoolerArtifact> codecoolerArtifacts = new ArrayList<CodecoolerArtifact>();
        Artifact artifact;
        Date purchaseDate;
        Date usageDate = null;

        //CodecoolerArtifact(Integer id, Artifact artifact, Date purchaseDate, Date usageDate)
        for (Map<String, String> result : results) {

            artifact = artifactDAO.get(Integer.valueOf(result.get("artifact_id")));
            purchaseDate = new Date(Long.valueOf(result.get("purchase_date")));

            if (result.get("use_date") != null) {
                usageDate = new Date(Long.valueOf(result.get("use_date")));
            } else {
                usageDate = null;
            }

            codecoolerArtifacts.add(new CodecoolerArtifact(Integer.valueOf(result.get("id")), 
                                                            artifact, purchaseDate, usageDate));
        }
        return codecoolerArtifacts;
    }

    public List<CodecoolerQuest> getCodecoolerQuestList(Integer id) {
        String sqlString = "SELECT * FROM codecooler_quest WHERE codecooler_id = ?;";
   
        List<Map<String, String>> results = executeSQLSelect(connection, sqlString, id);

        List<CodecoolerQuest> codecoolerQuests = new ArrayList<CodecoolerQuest>();
        Quest quest;
        Date markDate = null;

        //CodecoolerQuest(Integer id, Quest quest, Date markDate)
        for (Map<String, String> result : results) {

            quest = questDAO.get(Integer.valueOf(result.get("quest_id")));

            if (result.get("mark_date") != null) {
                markDate = new Date(Long.valueOf(result.get("mark_date")));
            } else {
                markDate = null;
            }

            codecoolerQuests.add(new CodecoolerQuest(Integer.valueOf(result.get("id")), quest, markDate));
        }
        return codecoolerQuests;
    }

    public List<Codecooler> getList() {
        String sqlString = "SELECT * FROM codecooler";

        List<Map<String, String>> results = executeSQLSelect(connection, sqlString);
        List<Codecooler> codecoolers = new ArrayList<Codecooler>();

        Classroom classroom;
        AppUser appUser;
        Integer id;
        Codecooler codecooler;

        //Codecooler(Integer id, AppUser appUser, Classroom classroom, Integer earnedCoolcoins, Integer wallet)
        for (Map<String, String> result : results) {

            classroom = classroomDAO.get(Integer.valueOf(result.get("classroom_id")));
            appUser = appUserDAO.get(result.get("appuser_login"));

            id = Integer.valueOf(result.get("id"));
            codecooler = new Codecooler(id, appUser, classroom, 
                                        Integer.valueOf(result.get("earned_coolcoins")), 
                                        Integer.valueOf(result.get("wallet")));

            codecooler.setArtifactList(getCodecoolerArtifactList(id)); 
            codecooler.setQuestList(getCodecoolerQuestList(id)); 

            codecoolers.add(codecooler);
        }
        return codecoolers;
    }

    public void assignCodecoolerToClass() {
        
    }

    public void updateWallet() {
        
    }

    public void addCodecoolerArtifact() {
        
    }

    public void updateCodecoolerArtifact() {
        
    }

    public void addCodecoolerQuest() {
        
    }

    public void updateCodecoolerQuest() {
        
    }
}