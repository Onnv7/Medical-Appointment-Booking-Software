package com.hcmute.findyourdoctor.Model;

import com.google.gson.annotations.SerializedName;
import com.hcmute.findyourdoctor.Model.Doctor;
import com.hcmute.findyourdoctor.Model.Review;

import java.io.Serializable;

public class Booking implements Serializable {
    @SerializedName("_id")
    private String id;
    private Doctor doctor;
    private String patient;
    private String message;
    private String status;
    private String image;
    private Review review;
    private float start;
    private String time;


    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
