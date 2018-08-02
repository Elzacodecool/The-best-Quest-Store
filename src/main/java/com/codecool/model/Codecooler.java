package com.codecool.model;

import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Codecooler extends AppUser {

    private Integer id;
    private Classroom classroom;
    private Integer earnedCoolcoins;
    private Integer wallet;
    private List<CodecoolerArtifact> artifactList;
    private List<CodecoolerQuest> questList;

    public Codecooler(String login, String password, String firstName, String lastName, String email) {
        super(login, password, firstName, lastName, email, "codecooler");
        this.artifactList = new ArrayList<CodecoolerArtifact>();
        this.questList = new ArrayList<CodecoolerQuest>();
    }

    // data from DB
    public Codecooler(Integer id, AppUser appUser, Classroom classroom, Integer earnedCoolcoins, Integer wallet) {
        super(appUser.getLogin(), 
                appUser.getPassword(), 
                appUser.getFirstName(), 
                appUser.getLastName(), 
                appUser.getEmail(), 
                appUser.getAppuserType());
        this.id = id;
        this.classroom = classroom;
        this.earnedCoolcoins = earnedCoolcoins;
        this.wallet = wallet;
    }

    public Integer getId() {
        return this.id;
    }

    public Classroom getClassroom() {
        return this.classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Integer getEarnedCoolcoins() {
        return this.earnedCoolcoins;
    }

    public void setEarnedCoolcoins(Integer earnedCoolcoins) {
        this.earnedCoolcoins = earnedCoolcoins;
    }

    public Integer getWallet() {
        return this.wallet;
    }

    public void setWallet(Integer wallet) {
        this.wallet = wallet;
    }

    public List<CodecoolerArtifact> getArtifactList() {
        return this.artifactList;
    }

    public void setArtifactList(List<CodecoolerArtifact> artifactList) {
        this.artifactList = artifactList;
    }

    public void addCodecoolerArtifact(CodecoolerArtifact codecoolerArtifact) {
        this.artifactList.add(codecoolerArtifact);
    }

    public List<CodecoolerQuest> getQuestList() {
        return this.questList;
    }

    public void setQuestList(List<CodecoolerQuest> questList) {
        this.questList = questList;
    }

    public void addCodecoolerQuest(CodecoolerQuest codecoolerQuest) {
        this.questList.add(codecoolerQuest);
    }

    public String toString() {

        StringBuilder artifacts = null;
        StringBuilder quests = null;

        if (this.artifactList != null) {
            artifacts = new StringBuilder();
            for (CodecoolerArtifact codecoolerArtifact : this.artifactList) {
                artifacts.append("\n");
                artifacts.append(codecoolerArtifact.toString());
                artifacts.append(", ");
            }
        }

        if (this.questList != null) {
            quests = new StringBuilder();
            for (CodecoolerQuest codecoolerQuest : this.questList) {
                quests.append("\n");
                quests.append(codecoolerQuest.toString());
                quests.append(", ");
            }
        }

        return String.format("[Codecooler] login: %s, id: %d, classroom: %s, earnedCoolcoins: %d, wallet: %d, \nartifacts: %s \nquests: %s", 
                            this.login, this.id, this.classroom.getName(), this.earnedCoolcoins, this.wallet, artifacts, quests);
    }

    // for sql query to insert codecooler to DB
    public Map<Integer, Object> toHashMapCodecooler() {

        Map<Integer, Object> codecoolerMap = new HashMap<>();
        codecoolerMap.put(1, this.login);
        codecoolerMap.put(2, this.classroom.getId());
        codecoolerMap.put(3, this.earnedCoolcoins);
        codecoolerMap.put(4, this.wallet);

        return codecoolerMap;
    }

    // for sql query to update codecooler in DB
    public Map<Integer, Object> toHashMapCodecoolerToUpdate() {

        Map<Integer, Object> codecoolerMap = new HashMap<>();
        codecoolerMap.put(1, this.classroom.getId());
        codecoolerMap.put(2, this.earnedCoolcoins);
        codecoolerMap.put(3, this.wallet);
        codecoolerMap.put(4, this.id);

        return codecoolerMap;
    }
}
