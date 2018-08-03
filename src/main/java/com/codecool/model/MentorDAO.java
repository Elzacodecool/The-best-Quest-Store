package com.codecool.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* columns in DB in table mentor:
        id
        appuser_login

   columns in DB in table mentor_classroom:
        mentor_id
        classroom_id
*/
public class MentorDAO extends CommonDAO {

    private Connection connection;
    private AppUserDAO appUserDAO;
    private CodecoolerDAO codecoolerDAO;
    private ClassroomDAO classroomDAO;

    public MentorDAO(Connection connection) {
        this.connection = connection;
        appUserDAO = new AppUserDAO(connection);
        codecoolerDAO = new CodecoolerDAO(connection);
        classroomDAO = new ClassroomDAO(connection);
    }

    public int add(Mentor mentor) {
        
        appUserDAO.add(mentor);

        String sqlString = "INSERT INTO mentor (appuser_login) VALUES (?);";
        return executeSQLUpdateDB(connection, sqlString, mentor.toHashMapMentor());
    }

    public int update(Mentor mentor) {

        // update mentor_classroom
        String sqlDelete = "DELETE FROM mentor_classroom WHERE mentor_id = ?;";
        String sqlInsert = "INSERT INTO mentor_classroom (mentor_id, classroom_id) VALUES (?, ?);";

        Map<Integer, Object> mentorClassroom = new HashMap<>();
        mentorClassroom.put(1, mentor.getId());

        executeSQLUpdateDB(connection, sqlDelete, mentorClassroom);

        for (Classroom classroom : mentor.getClassroomList()) {
            mentorClassroom.put(2, classroom.getId());                        
            executeSQLUpdateDB(connection, sqlInsert, mentorClassroom);
        }

        // update appuser
        return appUserDAO.update(mentor);
    }

    public Mentor get(AppUser appUser) {
        String sqlString = "SELECT * FROM mentor WHERE appuser_login = ?;";
        
        Map<String, String> result = executeSQLSelect(connection, sqlString, appUser.getLogin()).get(0);
        
        Integer id = Integer.valueOf(result.get("id"));

        //Mentor(Integer id, AppUser appUser)
        Mentor mentor = new Mentor(id, appUser);
        mentor.setClassroomList(getMentorClassroomList(id));
        
        return mentor; 
    }

    public Mentor get(Integer id) {
        String sqlString = "SELECT * FROM mentor WHERE id = ?;";
        
        Map<String, String> result = executeSQLSelect(connection, sqlString, id).get(0);

        AppUser appUser = appUserDAO.get(result.get("appuser_login"));

        //Mentor(Integer id, AppUser appUser)
        Mentor mentor = new Mentor(id, appUser);
        mentor.setClassroomList(getMentorClassroomList(id));
        
        return mentor;
    }

    public List<Classroom> getMentorClassroomList(Integer id) {
        String sqlString = "SELECT * FROM mentor_classroom WHERE mentor_id = ?;";
   
        List<Map<String, String>> results = executeSQLSelect(connection, sqlString, id);
        List<Classroom> classrooms = new ArrayList<Classroom>();

        for (Map<String, String> result : results) {
            classrooms.add(classroomDAO.get(Integer.valueOf(result.get("classroom_id"))));
        }
        return classrooms;
    }

    public List<Mentor> getList() {
        String sqlString  = "SELECT * FROM mentor";

        List<Map<String, String>> results = executeSQLSelect(connection, sqlString);
        List<Mentor> mentors = new ArrayList<Mentor>();

        AppUser appUser;
        Integer id;

        //Mentor(Integer id, AppUser appUser)
        for (Map<String, String> result : results) {

            appUser = appUserDAO.get(result.get("appuser_login"));
            id = Integer.valueOf(result.get("id"));

            //Mentor(Integer id, AppUser appUser)
            Mentor mentor = new Mentor(id, appUser);
            mentor.setClassroomList(getMentorClassroomList(id));

            mentors.add(mentor);
        }
        return mentors;
    }

    public List<Codecooler> getMentorCodecoolerList(Integer id) {
        String sqlString = "SELECT id FROM codecooler WHERE classroom_id IN " +
                            "(SELECT classroom_id FROM mentor_classroom WHERE mentor_id = ?);";
   
        List<Map<String, String>> results = executeSQLSelect(connection, sqlString, id);
        List<Codecooler> codecoolers = new ArrayList<Codecooler>();

        for (Map<String, String> result : results) {
            codecoolers.add(codecoolerDAO.get(Integer.valueOf(result.get("id"))));
        }
        return codecoolers;
    }
}

//TO DO assignMentorToClass