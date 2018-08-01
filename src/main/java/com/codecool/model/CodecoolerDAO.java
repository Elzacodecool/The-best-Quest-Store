package com.codecool.model;

import java.sql.Connection;
import java.util.ArrayList;
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

    public CodecoolerDAO(Connection connection) {
        this.connection = connection;
        appUserDAO = new AppUserDAO(connection);
        classroomDAO = new ClassroomDAO(connection);
    }

    public int add(Codecooler codecooler) {
        
        appUserDAO.add(codecooler);

        String sqlString = "INSERT INTO codecooler (appuser_login, classroom_id, earned_coolcoins, wallet) VALUES (?, ?, ?, ?);";
        return executeSQLUpdateDB(connection, sqlString, codecooler.toHashMapCodecooler());
    }

    public int update(Codecooler codecooler) {

        appUserDAO.update((AppUser) codecooler);

        String sqlString = "UPDATE codecooler SET classroom_id = ?, earned_coolcoins = ?, wallet = ? WHERE id = ?;";
        return executeSQLUpdateDB(connection, sqlString, codecooler.toHashMapCodecoolerToUpdate());
    }

    public Codecooler get(AppUser appUser) {
        String sqlString = "SELECT * FROM codecooler WHERE appuser_login = ?;";
        
        Map<String, String> result = executeSQLSelect(connection, sqlString, appUser.getLogin()).get(0);

        Classroom classroom = classroomDAO.get(Integer.valueOf(result.get("classroom_id")));

        //Codecooler(Integer id, AppUser appUser, Classroom classroom, Integer earnedCoolcoins, Integer wallet)
        return new Codecooler(Integer.valueOf(result.get("id")), appUser, classroom, 
                                Integer.valueOf(result.get("earned_coolcoins")), 
                                Integer.valueOf(result.get("wallet")));
    }

    public Codecooler get(Integer id) {
        String sqlString = "SELECT * FROM codecooler WHERE id = ?;";
        
        Map<String, String> result = executeSQLSelect(connection, sqlString, id).get(0);

        Classroom classroom = classroomDAO.get(Integer.valueOf(result.get("classroom_id")));
        AppUser appUser = appUserDAO.get(result.get("appuser_login"));

        //Codecooler(Integer id, AppUser appUser, Classroom classroom, Integer earnedCoolcoins, Integer wallet)
        return new Codecooler(Integer.valueOf(result.get("id")), appUser, classroom, 
                                Integer.valueOf(result.get("earned_coolcoins")), 
                                Integer.valueOf(result.get("wallet")));
    }

    public List<Codecooler> getList() {
        String sql = String.format("SELECT * FROM codecooler");

        List<Map<String, String>> results = executeSQLSelect(connection, sql);
        List<Codecooler> codecoolers = new ArrayList<Codecooler>();

        Classroom classroom;
        AppUser appUser;

        //Codecooler(Integer id, AppUser appUser, Classroom classroom, Integer earnedCoolcoins, Integer wallet)
        for (Map<String, String> result : results) {

            classroom = classroomDAO.get(Integer.valueOf(result.get("classroom_id")));
            appUser = appUserDAO.get(result.get("appuser_login"));

            codecoolers.add(new Codecooler(Integer.valueOf(result.get("id")), appUser, classroom, 
                                            Integer.valueOf(result.get("earned_coolcoins")), 
                                            Integer.valueOf(result.get("wallet"))));
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