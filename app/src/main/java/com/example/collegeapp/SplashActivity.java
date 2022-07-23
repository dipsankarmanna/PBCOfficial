package com.example.collegeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.collegeapp.admin.Admin_home_Activity;
import com.example.collegeapp.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.startImg.setTranslationY(700);
        binding.startImg.setAlpha(1);
        binding.lay.setTranslationX(800);
        binding.lay.setAlpha(1);
        binding.startImg.animate().translationY(0).alpha(1).setDuration(600).setStartDelay(200).start();
        binding.lay.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(150).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
            }
        },3000);

    }
}