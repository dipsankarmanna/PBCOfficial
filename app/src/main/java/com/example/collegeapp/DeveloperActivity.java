package com.example.collegeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.collegeapp.databinding.ActivityDeveloperBinding;

public class DeveloperActivity extends AppCompatActivity {

    ActivityDeveloperBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDeveloperBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Developers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.dip.setTranslationX(-800);
        binding.dip.setAlpha(1);
        binding.suva.setTranslationX(-800);
        binding.suva.setAlpha(1);
        binding.saikat.setTranslationX(800);
        binding.saikat.setAlpha(1);
        binding.sayan.setTranslationX(800);
        binding.sayan.setAlpha(1);
        binding.dip.animate().translationX(0).alpha(1).setDuration(450).setStartDelay(250).start();
        binding.suva.animate().translationX(0).alpha(1).setDuration(450).setStartDelay(200).start();
        binding.saikat.animate().translationX(0).alpha(1).setDuration(450).setStartDelay(200).start();
        binding.sayan.animate().translationX(0).alpha(1).setDuration(450).setStartDelay(200).start();
        binding.dip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(DeveloperActivity.this,AboutDeveloperActivity.class);
                    intent.putExtra("img",R.drawable.dip);
                    intent.putExtra("name","Dipsankar Manna");
                    intent.putExtra("facebook","https://m.facebook.com/100048305138167/");
                    intent.putExtra("insta","");
                    intent.putExtra("twitter","");
                    intent.putExtra("linkedin","https://www.linkedin.com/in/dipsankar-manna-26016a200");
                    startActivity(intent);
            }
        });
        binding.saikat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(DeveloperActivity.this,AboutDeveloperActivity.class);
                    intent.putExtra("img",R.drawable.saikat);
                    intent.putExtra("name","Saikat Mondal");
                    intent.putExtra("facebook","https://www.facebook.com/tapumondal.saikatmondal");
                    intent.putExtra("insta","");
                    intent.putExtra("twitter","");
                    intent.putExtra("linkedin","");
                    startActivity(intent);
            }
        });
        binding.suva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(DeveloperActivity.this,AboutDeveloperActivity.class);
                    intent.putExtra("img",R.drawable.suva);
                    intent.putExtra("name","Suvadip Das");
                    intent.putExtra("facebook","https://www.facebook.com/suvadip.das.372019");
                    intent.putExtra("insta","");
                    intent.putExtra("twitter","");
                    intent.putExtra("linkedin","");
                    startActivity(intent);
            }
        });
        binding.sayan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(DeveloperActivity.this,AboutDeveloperActivity.class);
                    intent.putExtra("img",R.drawable.sayan);
                    intent.putExtra("name","Sayan Mallick");
                    intent.putExtra("facebook","https://www.facebook.com/profile.php?id=100038173980300");
                    intent.putExtra("insta","");
                    intent.putExtra("twitter","");
                    intent.putExtra("linkedin","");
                    startActivity(intent);
            }
        });
    }
}