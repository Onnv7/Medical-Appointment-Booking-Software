package com.hcmute.findyourdoctor.Domain;

public class BookingDomain {
    private String patientId;
    private String doctorId;
    private String message;
    private String status;
    private String advice;
    private String review;
    private Number start;
    private String schedule;

    public BookingDomain(String patientId, String doctorId, String message, String status, String advice, String review, Number start, String schedule) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.message = message;
        this.status = status;
        this.advice = advice;
        this.review = review;
        this.start = start;
        this.schedule = schedule;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
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

    public Number getStart() {
        return start;
    }

    public void setStart(Number start) {
        this.start = start;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
