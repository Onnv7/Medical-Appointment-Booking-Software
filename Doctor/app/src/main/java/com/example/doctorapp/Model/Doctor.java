package com.example.doctorapp.Model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Doctor implements Serializable {
    @SerializedName("_id")
    private String id;
    private String avatarUrl;
    private String name;
    private Specialist specialist;
    private float price;
    private float rating;
    private String clinicName;
    private String clinicAddress;
    private String introduction;
    private int patientQuantity;
    private float start;
    private String phone;
    private String birthDate;
    private String gender;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Doctor() {
    }

    public String getIntroduction() {
        return introduction;
    }

    public float getStart() {
        return start;
    }

    public void setStart(float start) {
        this.start = start;
    }

    public int getPatientQuantity() {
        return patientQuantity;
    }

    public void setPatientQuantity(int patientQuantity) {
        this.patientQuantity = patientQuantity;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getClinicAddress() {
        return clinicAddress;
    }

    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public String getIntroduce() {
        return introduction;
    }

    public void setIntroduction(String introduce) {
        this.introduction = introduce;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }


    @Override
    public String toString() {
        return "Doctor{" +
                "id='" + id + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", name='" + name + '\'' +
                ", specialist=" + specialist +
                ", price=" + price +
                ", rating=" + rating +
                ", clinicName='" + clinicName + '\'' +
                ", clinicAddress='" + clinicAddress + '\'' +
                ", introduction='" + introduction + '\'' +
                ", patientQuantity=" + patientQuantity +
                ", start=" + start +
                ", phone='" + phone + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
