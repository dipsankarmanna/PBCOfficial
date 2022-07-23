package com.example.collegeapp.user.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.collegeapp.MainActivity;
import com.example.collegeapp.admin.Admin_home_Activity;
import com.example.collegeapp.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    String email,password;
    private FirebaseAuth auth;
    ProgressDialog dialog;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME="my_pref";
    private static final String KEY_STATUS="email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if (sharedPreferences.contains(KEY_STATUS) ) {
            Intent intent = new Intent(getApplicationContext(), Admin_home_Activity.class);
            startActivity(intent);
            finish();
        }

        auth=FirebaseAuth.getInstance();
        dialog=new ProgressDialog(LoginActivity.this);
        dialog.setCancelable(false);
        dialog.setMessage("Signing In...");
        getSupportActionBar().hide();
        binding.card.setTranslationY(800);
        binding.card.setAlpha(1);
        binding.clgname.setTranslationY(800);
        binding.clgname.setAlpha(1);
        binding.clgicon.setTranslationX(800);
        binding.clgicon.setAlpha(1);
        binding.login.setTranslationX(800);
        binding.login.setAlpha(1);
        binding.lay.setTranslationY(800);
        binding.lay.setAlpha(1);
        binding.lay.animate().translationY(0).alpha(1).setDuration(600).setStartDelay(250).start();
        binding.card.animate().translationY(0).alpha(1).setDuration(500).setStartDelay(400).start();
        binding.clgname.animate().translationY(0).alpha(1).setDuration(500).setStartDelay(200).start();
        binding.clgicon.animate().translationX(0).alpha(1).setDuration(500).setStartDelay(200).start();
        binding.login.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(200).start();
        binding.openRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                finish();
            }
        });
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=binding.logEmail.getText().toString();
                password=binding.logPass.getText().toString();
                if(email.equals("adminpbc@gmail.com") && password.equals("admin@123")){
                    editor.putString(KEY_STATUS,"admin");
                    editor.apply();
                    startActivity(new Intent(getApplicationContext(), Admin_home_Activity.class));
                    finish();
                    Toast.makeText(LoginActivity.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                }else {
                    validateData();
                }
            }
        });
    }

    private void validateData() {
        email=binding.logEmail.getText().toString();
        password=binding.logPass.getText().toString();
        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Enter details Properly", Toast.LENGTH_SHORT).show();
        }else {
            loginUser();
        }
    }

    private void loginUser() {
        dialog.show();
        auth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        dialog.dismiss();
                        openMain();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void openMain() {

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

    }
}