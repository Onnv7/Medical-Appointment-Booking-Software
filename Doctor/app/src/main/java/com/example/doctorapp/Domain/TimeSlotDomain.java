package com.example.doctorapp.Domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TimeSlotDomain implements Serializable {
    @SerializedName("_id")
    private String id;
    private String start;
    private Boolean selected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "ScheduleDomain{" +
                "id='" + id + '\'' +
                ", start='" + start + '\'' +
                ", selected=" + selected +
                '}';
    }
}
