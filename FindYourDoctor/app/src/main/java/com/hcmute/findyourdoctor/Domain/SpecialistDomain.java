package com.hcmute.findyourdoctor.Domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SpecialistDomain implements Serializable {

    @SerializedName("_id")
    private String id;
    private String name;
    private int doctorQuantity;
    private String imageUrl;


    public SpecialistDomain(String id, String name, int doctorQuantity, String imageUrl) {
        this.id = id;
        this.name = name;
        this.doctorQuantity = doctorQuantity;
        this.imageUrl = imageUrl;
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

    public int getDoctorQuantity() {
        return doctorQuantity;
    }

    public void setDoctorQuantity(int doctorQuantity) {
        this.doctorQuantity = doctorQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
