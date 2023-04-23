package com.hcmute.findyourdoctor.Domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SpecialistDomain implements Parcelable {
    private String title;
    private String pic;
    private int quantityDoctor;

    public SpecialistDomain(String title, String pic) {
        this.title = title;
        this.pic = pic;
    }

    protected SpecialistDomain(Parcel in) {
        title = in.readString();
        pic = in.readString();
    }

    public SpecialistDomain(String title, String pic, int quantityDoctor) {
        this.title = title;
        this.pic = pic;
        this.quantityDoctor = quantityDoctor;
    }

    public static final Creator<SpecialistDomain> CREATOR = new Creator<SpecialistDomain>() {
        @Override
        public SpecialistDomain createFromParcel(Parcel in) {
            return new SpecialistDomain(in);
        }

        @Override
        public SpecialistDomain[] newArray(int size) {
            return new SpecialistDomain[size];
        }
    };

    public int getQuantityDoctor() {
        return quantityDoctor;
    }

    public void setQuantityDoctor(int quantityDoctor) {
        this.quantityDoctor = quantityDoctor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "SpecialistDomain{" +
                "title='" + title + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(pic);
    }
}
