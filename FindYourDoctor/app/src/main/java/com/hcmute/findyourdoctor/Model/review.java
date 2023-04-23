package com.hcmute.findyourdoctor.Model;

import java.io.Serializable;
import java.util.List;

public class review implements Serializable {
    private String id;
    private String patientName;
    private String avatarUrl;
    private String description;
    private float star;
    private List<String> liker;
    private String createdAt;


    public review(String id, String description, float star, String createdAt) {
        this.id = id;
        this.description = description;
        this.star = star;
        this.createdAt = createdAt;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public List<String> getLiker() {
        return liker;
    }

    public void setLiker(List<String> liker) {
        this.liker = liker;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
