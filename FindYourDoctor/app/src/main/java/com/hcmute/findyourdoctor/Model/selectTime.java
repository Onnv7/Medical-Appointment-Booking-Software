package com.hcmute.findyourdoctor.Model;

public class selectTime {
    private String date;
    private String slot;

    public selectTime(String date, String slot) {
        this.date = date;
        this.slot = slot;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }
}
