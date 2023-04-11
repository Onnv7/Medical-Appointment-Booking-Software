package com.hcmute.findyourdoctor.Domain;

public class PopularDoctorDomain {
    private String id;
    private String name;
    private String specialist;
    private float start;
    private String avatarUrl;

    public PopularDoctorDomain(String id, String name, String specialist, float start, String avatarUrl) {
        this.id = id;
        this.name = name;
        this.specialist = specialist;
        this.start = start;
        this.avatarUrl = avatarUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public float getStart() {
        return start;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setStart(float start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "PopularDoctorDomain{" +
                "name='" + name + '\'' +
                ", specialist='" + specialist + '\'' +
                ", start=" + start +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
