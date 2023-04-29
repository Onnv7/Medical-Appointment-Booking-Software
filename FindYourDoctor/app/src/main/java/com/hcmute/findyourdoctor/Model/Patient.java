package com.hcmute.findyourdoctor.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Patient implements Serializable {
    @SerializedName("_id")
    private String id;
    private String name;
    private String avatarUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
