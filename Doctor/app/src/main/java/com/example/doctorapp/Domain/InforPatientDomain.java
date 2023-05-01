package com.example.doctorapp.Domain;

public class InforPatientDomain {
    private String time;
    private String name;
    private String day;

    public InforPatientDomain(String time, String name, String day) {
        this.time = time;
        this.name = name;
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
