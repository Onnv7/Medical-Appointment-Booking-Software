package com.hcmute.findyourdoctor.Domain;

public class PatientDomain {
    private String email;
    private String password;
    private String gender;
    private String birth_date;
    private String avatar_url;
    private String phone;
    private String address;

    public PatientDomain(String email, String password, String gender, String birth_date, String avatar_url, String phone, String address) {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birth_date = birth_date;
        this.avatar_url = avatar_url;
        this.phone = phone;
        this.address = address;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
