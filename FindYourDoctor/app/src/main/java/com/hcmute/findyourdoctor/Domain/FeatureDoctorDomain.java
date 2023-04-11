package com.hcmute.findyourdoctor.Domain;

public class FeatureDoctorDomain {
    private String id;
    private String avatarUlr;
    private String name;
    private float rating;
    private float price;

    public FeatureDoctorDomain(String id, String avatarUlr, String name, float rating, float price) {
        this.id = id;
        this.avatarUlr = avatarUlr;
        this.name = name;
        this.rating = rating;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatarUlr() {
        return avatarUlr;
    }

    public void setAvatarUlr(String avatarUlr) {
        this.avatarUlr = avatarUlr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "FeatureDoctorDomain{" +
                "avatarUlr='" + avatarUlr + '\'' +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", price=" + price +
                '}';
    }
}
