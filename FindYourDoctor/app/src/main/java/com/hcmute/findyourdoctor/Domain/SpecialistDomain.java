package com.hcmute.findyourdoctor.Domain;

public class SpecialistDomain {
    private String title;
    private String pic;

    public SpecialistDomain(String title, String pic) {
        this.title = title;
        this.pic = pic;
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
}
