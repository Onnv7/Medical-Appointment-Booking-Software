package com.hcmute.findyourdoctor.Domain;

public class DoctorDomain {
    private String email;
    private String password;
    private String name;
    private String gender;
    private String birthDate;
    private String avatarUrl;
    private String phone;
    private String introduction;
    private String clinicName;
    private String clinicAddress;
    private Number price;
    private SpecialistDomain specialistId;

    public DoctorDomain(String email, String password, String name, String gender, String birthDate, String avatarUrl, String phone, String introduction, String clinicName, String clinicAddress, Number price, SpecialistDomain specialistId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.avatarUrl = avatarUrl;
        this.phone = phone;
        this.introduction = introduction;
        this.clinicName = clinicName;
        this.clinicAddress = clinicAddress;
        this.price = price;
        this.specialistId = specialistId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
    }

    public SpecialistDomain getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(SpecialistDomain specialistId) {
        this.specialistId = specialistId;
    }
}
