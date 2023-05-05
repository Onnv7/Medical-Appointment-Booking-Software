package com.hcmute.findyourdoctor.Domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TimeSlotDomain implements Serializable {
    @SerializedName("_id")
    private String id;
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
