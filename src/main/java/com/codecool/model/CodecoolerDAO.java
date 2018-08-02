package com.codecool.model;

import java.util.ArrayList;
import java.util.List;

public class CodecoolerDAO {
    
    public void add(Codecooler codecooler) {

    }

    public void update(Codecooler codecooler) {

    }

    public void get(int id) {

    }

    public List<Codecooler> getList() {
        List<Codecooler> codecoolers = new ArrayList<>();
        Codecooler codecooler = new Codecooler("login", "pass", "name", "lname", "email", "codecooler");
        codecoolers.add(codecooler);
        codecoolers.add(codecooler);
        codecoolers.add(codecooler);
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