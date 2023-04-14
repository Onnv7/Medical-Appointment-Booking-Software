package com.hcmute.findyourdoctor.Model;

import java.io.Serializable;

public class selectTime implements Serializable {
    private String date;
    private int slot;

    public selectTime(String date, int slot) {
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
