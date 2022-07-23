package com.example.collegeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.collegeapp.admin.Admin_home_Activity;
import com.example.collegeapp.databinding.ActivityWelcomeBinding;
import com.example.collegeapp.user.authentication.LoginActivity;

public class WelcomeActivity extends AppCompatActivity {

    ActivityWelcomeBinding binding;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME="my_pref";
    private static final String KEY_STATUS="email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if (sharedPreferences.contains(KEY_STATUS) ) {
            Intent intent = new Intent(getApplicationContext(), Admin_home_Activity.class);
            startActivity(intent);
            finish();
        }
        binding.startImg.setTranslationY(700);
        binding.startImg.setAlpha(1);
        binding.lay.setTranslationX(800);
        binding.lay.setAlpha(1);
        binding.start.setTranslationX(800);
        binding.start.setAlpha(1);
        binding.startImg.animate().translationY(0).alpha(1).setDuration(500).setStartDelay(200).start();
        binding.lay.animate().translationX(0).alpha(1).setDuration(500).setStartDelay(150).start();
        binding.start.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(200).start();
        binding.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
            }
        });

    }
}