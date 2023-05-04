package com.example.doctorapp.Domain;

import com.example.doctorapp.Model.Booking;

import java.io.Serializable;

public class ScheduleDomain implements Serializable {
    private String start;
    private Boolean selected;

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
}
