package com.example.collegeapp.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.collegeapp.R;
import com.example.collegeapp.admin.ebook.Check_Ebook;
import com.example.collegeapp.admin.faculty.updatefaculty;
import com.example.collegeapp.admin.image.CheckImage;
import com.example.collegeapp.admin.notice.DeleteNoticeActivity;
import com.example.collegeapp.admin.notice.UploadNotice;
import com.example.collegeapp.databinding.ActivityAdminHomeBinding;
import com.example.collegeapp.user.authentication.LoginActivity;

public class Admin_home_Activity extends AppCompatActivity {
    ActivityAdminHomeBinding binding;
    AlertDialog.Builder builder;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME="my_pref";
    private static final String KEY_STATUS="email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        builder=new AlertDialog.Builder(this);
        /*sharedPreferences=this.getSharedPreferences("my_pref",MODE_PRIVATE);
        editor=sharedPreferences.edit();*/

        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        Toast.makeText(this, "Welcome Admin", Toast.LENGTH_SHORT).show();
        binding.addNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), UploadNotice.class);
                startActivity(intent);
            }
        });
        binding.addGalleryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), CheckImage.class);
                startActivity(intent);
            }
        });
        binding.addEbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Check_Ebook.class);
                startActivity(intent);
            }
        });
        binding.faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), updatefaculty.class);
                startActivity(intent);
            }
        });
        binding.deleteNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), DeleteNoticeActivity.class);
                startActivity(intent);
            }
        });
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Are sure to Log Out?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editor.clear();
                        editor.commit();
                        openLogin();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        });
    }

    private void openLogin() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}