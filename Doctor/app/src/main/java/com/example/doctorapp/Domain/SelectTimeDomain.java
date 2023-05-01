package com.example.doctorapp.Domain;

import java.io.Serializable;

public class SelectTimeDomain implements Serializable {
    private String date;
    private int slot;

    public SelectTimeDomain(String date, int slot) {
        this.date = date;
        this.slot = slot;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
