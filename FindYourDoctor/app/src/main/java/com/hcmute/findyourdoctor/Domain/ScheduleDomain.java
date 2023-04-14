package com.hcmute.findyourdoctor.Domain;

import java.util.Date;

public class ScheduleDomain {
    private Date date;
    private String start;
    private String end;
    private DoctorDomain doctorId;

    public ScheduleDomain(Date date, String start, String end, DoctorDomain doctorId) {
        this.date = date;
        this.start = start;
        this.end = end;
        this.doctorId = doctorId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public DoctorDomain getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(DoctorDomain doctorId) {
        this.doctorId = doctorId;
    }
}
