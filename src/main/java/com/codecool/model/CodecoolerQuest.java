package com.codecool.model;

import java.util.Date;

public class CodecoolerQuest {

    private int id;
    private Quest quest;
    private Date markDate;

    public CodecoolerQuest(Quest quest) {
        this.quest = quest;
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
}