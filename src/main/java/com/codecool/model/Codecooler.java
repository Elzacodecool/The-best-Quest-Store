package com.codecool.model;

import java.util.ArrayList;
import java.util.List;


public class Codecooler extends AppUser {

    private int id;
    private Classroom classroom;
    private int earnedCoolcoins;
    private int wallet;
    private List<CodecoolerArtifact> artifactList;
    private List<CodecoolerQuest> questList;

    public Codecooler(String login, String password, String firstName, String lastName, String email, String appuserType) {
        super(login, password, firstName, lastName, email, appuserType);
        this.artifactList = new ArrayList<CodecoolerArtifact>();
        this.questList = new ArrayList<CodecoolerQuest>();
    }

    public int getId() {
        return this.id;
    }

    public Classroom getClassroom() {
        return this.classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public int getEarnedCoolcoins() {
        return this.earnedCoolcoins;
    }

    public void setEarnedCoolcoins(int earnedCoolcoins) {
        this.earnedCoolcoins = earnedCoolcoins;
    }

    public int getWallet() {
        return this.wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public List<CodecoolerArtifact> getArtifactList() {
        return this.artifactList;
    }

    public List<CodecoolerQuest> getQuestList() {
        return this.questList;
    }
} 
