package com.hcmute.findyourdoctor.Domain;

public class FeatureDoctorDomain {
    private String image;
    private String name;
    private int number;
    private int price;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public FeatureDoctorDomain(String image, String name, int number, int price) {
        this.image = image;
        this.name = name;
        this.number = number;
        this.price = price;
    }
}
