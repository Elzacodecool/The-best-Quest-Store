package com.codecool.model;

import java.util.Date;

public class CodecoolerQuest {

    private int id;
    private Quest quest;
    private Date markDate;

    public CodecoolerQuest(Quest quest, Date markDate) {
        this.quest = quest;
        this.markDate = markDate;
    }

    // data from DB
    public CodecoolerQuest(Integer id, Quest quest, Date markDate) {
        this.id = id;
        this.quest = quest;
        this.markDate = markDate;
    }

    public int getId() {
        return this.id;
    }

    public Quest getQuest() {
        return this.quest;
    }

    public Date getMarkDate() {
        return this.markDate;
    }

    public void setMarkDate(Date markDate) {
        this.markDate = markDate;
    }

    public String toString() {
        return String.format("\n[CodecoolerQuest] id: %d, quest name: %s, markDate: %s", 
                            this.id, this.quest.getName(), this.markDate);
    }
}