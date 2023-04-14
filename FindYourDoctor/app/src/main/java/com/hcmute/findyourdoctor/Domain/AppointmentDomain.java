package com.hcmute.findyourdoctor.Domain;

public class AppointmentDomain {
    private String name;
    private String status;
    private String image;
    private String time;

    public AppointmentDomain(String name, String status, String image, String time) {
        this.name = name;
        this.status = status;
        this.image = image;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
