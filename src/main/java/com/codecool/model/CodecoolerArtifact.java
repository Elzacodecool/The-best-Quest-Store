package com.codecool.model;

import java.util.Date;

public class CodecoolerArtifact {

    private int id;
    private Artifact artifact;
    private Date purchaseDate;
    private Date usageDate;

    public CodecoolerArtifact(Artifact artifact, Date purchaseDate) {
        this.artifact = artifact;
        this.purchaseDate = purchaseDate;
    }

    public int getId() {
        return this.id;
    }

    public Artifact getArtifact() {
        return this.artifact;
    }

    public Date getPurchaseDate() {
        return this.purchaseDate;
    }

    public Date getUsageDate() {
        return this.usageDate;
    }

    public void setUsageDate(Date usageDate) {
        this.usageDate = usageDate;
    }
}