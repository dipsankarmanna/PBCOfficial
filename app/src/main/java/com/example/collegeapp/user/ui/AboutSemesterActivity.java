package com.example.collegeapp.user.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.collegeapp.R;
import com.example.collegeapp.databinding.ActivityAboutSemesterBinding;

public class AboutSemesterActivity extends AppCompatActivity {
    ActivityAboutSemesterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAboutSemesterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
    }
}