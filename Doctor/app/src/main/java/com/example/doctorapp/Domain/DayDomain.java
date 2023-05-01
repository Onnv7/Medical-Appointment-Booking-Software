package com.example.doctorapp.Domain;

public class DayDomain {
    private String day;
    private String oneDay;

    public DayDomain(String day, String oneDay) {
        this.day = day;
        this.oneDay = oneDay;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getOneDay() {
        return oneDay;
    }

    public void setOneDay(String oneDay) {
        this.oneDay = oneDay;
    }
}
