package com.example.collegeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.collegeapp.databinding.ActivityAboutDeveloperBinding;
import com.squareup.picasso.Picasso;

public class AboutDeveloperActivity extends AppCompatActivity {

    ActivityAboutDeveloperBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAboutDeveloperBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        int dev_img=getIntent().getIntExtra("img",0);
        String dev_name=getIntent().getStringExtra("name");
        String face=getIntent().getStringExtra("facebook");
        String insta=getIntent().getStringExtra("insta");
        String twitter=getIntent().getStringExtra("twitter");
        String linkedin=getIntent().getStringExtra("linkedin");
        binding.devImg.setImageResource(dev_img);
        binding.name.setText(dev_name);
        getSupportActionBar().hide();

        binding.facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(face));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        binding.insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(insta));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        binding.twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitter));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        binding.linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkedin));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}