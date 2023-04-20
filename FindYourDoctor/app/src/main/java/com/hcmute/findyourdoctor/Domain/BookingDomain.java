package com.hcmute.findyourdoctor.Domain;

import java.io.Serializable;

public class BookingDomain implements Serializable {
    private String patient;
    private String doctor;
    private String message;
    private String status;
    private String advice;
    private String review;
    private float start;
    private String time;

    public BookingDomain(String patient, String doctor, String message, String status, String time) {
        this.patient = patient;
        this.doctor = doctor;
        this.message = message;
        this.status = status;
        this.time = time;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getStart() {
        return start;
    }

    public void setStart(float start) {
        this.start = start;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
