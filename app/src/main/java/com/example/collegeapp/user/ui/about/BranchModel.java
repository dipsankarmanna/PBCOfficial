package com.example.collegeapp.user.ui.about;

public class BranchModel {
    int img;int bac;
    String title,description;

    public BranchModel(int img,int bac, String title, String description) {
        this.img = img;
        this.bac=bac;
        this.title = title;
        this.description = description;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getBac() {
        return bac;
    }

    public void setBac(int bac) {
        this.bac = bac;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
