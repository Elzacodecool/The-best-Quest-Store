package com.codecool.model;

import java.util.HashMap;

public class Team {

    private int id;
    private Artifact artifact;
    private Codecooler leader;
    private String status;
    private HashMap<Codecooler, Integer> codecoolerMap;

    public Team(Artifact artifact, Codecooler leader) {
        this.artifact = artifact;
        this.leader = leader;
    }

    public int getId() {
        return this.id;
    }

    public Artifact getArtifact() {
        return this.artifact;
    }

    public Codecooler getLeader() {
        return this.leader;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HashMap<Codecooler, Integer> getCodecoolerMap() {
        return this.codecoolerMap;
    }

    public void addCodecooler(Codecooler codecooler, Integer coolcoins) {
        codecoolerMap.put(codecooler, coolcoins);
    }
}