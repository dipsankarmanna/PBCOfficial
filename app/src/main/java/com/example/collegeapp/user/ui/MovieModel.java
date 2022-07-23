package com.example.collegeapp.user.ui;

import android.content.Context;

public class MovieModel {
    String branch_name,branch_desc;
    int img;
    Context context;

    public MovieModel() {
    }

    public MovieModel(Context context, String branch_name, String branch_desc, int img) {
        this.branch_name = branch_name;
        this.branch_desc = branch_desc;
        this.img = img;
        this.context=context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getBranch_desc() {
        return branch_desc;
    }

    public void setBranch_desc(String branch_desc) {
        this.branch_desc = branch_desc;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}

