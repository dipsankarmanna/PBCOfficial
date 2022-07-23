package com.example.collegeapp.user.ui;

public class Semester_Details {
    String dept,semester;
    int image;

    public Semester_Details(String dept, String semester, int image) {
        this.dept = dept;
        this.semester = semester;
        this.image = image;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
