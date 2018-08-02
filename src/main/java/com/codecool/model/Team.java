package com.codecool.model;

import java.lang.StringBuilder;
import java.util.HashMap;
import java.util.Map;

public class Team {

    private Integer id;
    private String name;
    private Artifact artifact;
    private Codecooler leader;
    private String status;
    private HashMap<Codecooler, Integer> codecoolerMap;

    public Team(String name, Artifact artifact, Codecooler leader, String status) {
        this.name = name;
        this.artifact = artifact;
        this.leader = leader;
        this.status = status;
    }

    // data from DB
    public Team(Integer id, String name, Artifact artifact, Codecooler leader, String status) {
        this.id = id;
        this.name = name;
        this.artifact = artifact;
        this.leader = leader;
        this.status = status;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String toString() {

        StringBuilder members = null;

        if (this.codecoolerMap != null) {
            members = new StringBuilder();
            for (Codecooler codecooler : this.codecoolerMap.keySet()) {
                members.append(codecooler.getLogin());
                members.append(" give ");
                members.append(codecoolerMap.get(codecooler));
                members.append(" coins, ");
            }
        }

        return String.format("[Team] id: %d, name: %s, artifact name: %s, leader login: %s, status: %s, team members: %s", 
                            this.id, this.name, this.artifact.getName(), this.leader.getLogin(), this.status, members);
    }

    // for sql query to insert team to DB
    public Map<Integer, Object> toHashMap() {

        Map<Integer, Object> teamMap = new HashMap<>();
        teamMap.put(1, this.name);
        teamMap.put(2, this.artifact.getId());
        teamMap.put(3, this.leader.getId());
        teamMap.put(4, this.status);

        return teamMap;
    }

    // for sql query to update quest in DB
    public Map<Integer, Object> toHashMapWithId() {

        Map<Integer, Object> teamMap = toHashMap();
        teamMap.put(5, this.id);

        return teamMap;
    }
}