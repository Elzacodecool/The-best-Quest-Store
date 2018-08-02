package com.codecool.model;

import java.util.Date;

public class CodecoolerArtifact {

    private Integer id;
    private Artifact artifact;
    private Date purchaseDate;
    private Date usageDate;

    public CodecoolerArtifact(Artifact artifact, Date purchaseDate, Date usageDate) {
        this.artifact = artifact;
        this.purchaseDate = purchaseDate;
        this.usageDate = usageDate;
    }

    // data from DB
    public CodecoolerArtifact(Integer id, Artifact artifact, Date purchaseDate, Date usageDate) {
        this.id = id;
        this.artifact = artifact;
        this.purchaseDate = purchaseDate;
        this.usageDate = usageDate;
    }

    public Integer getId() {
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

    public String toString() {
        return String.format("[CodecoolerArtifact] id: %d, artifact name: %s, purchaseDate: %s, usageDate: %s", 
                            this.id, this.artifact.getName(), this.purchaseDate, this.usageDate);
    }
}