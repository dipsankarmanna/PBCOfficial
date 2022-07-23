package com.example.collegeapp.user.ui;

import android.content.Context;

public class SemesterModel {
    int images;
    Context context;

    public SemesterModel() {
    }

    public SemesterModel(int images, Context context) {
        this.images = images;
        this.context = context;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
