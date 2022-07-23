package com.example.collegeapp.user.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.collegeapp.MainActivity;
import com.example.collegeapp.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;

    String name,email,password;
    FirebaseAuth auth;
    DatabaseReference reference;
    DatabaseReference dbref;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        auth=FirebaseAuth.getInstance();
        dialog=new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Submitting.Please Wait...");
        reference=FirebaseDatabase.getInstance().getReference();

        binding.card.setTranslationY(800);
        binding.card.setAlpha(1);
        binding.clgname.setTranslationY(800);
        binding.clgname.setAlpha(1);
        binding.clgicon.setTranslationX(800);
        binding.clgicon.setAlpha(1);
        binding.register.setTranslationX(800);
        binding.register.setAlpha(1);
        binding.lay.setTranslationY(800);
        binding.lay.setAlpha(1);
        binding.lay.animate().translationY(0).alpha(1).setDuration(600).setStartDelay(250).start();
        binding.card.animate().translationY(0).alpha(1).setDuration(500).setStartDelay(400).start();
        binding.clgname.animate().translationY(0).alpha(1).setDuration(500).setStartDelay(200).start();
        binding.clgicon.animate().translationX(0).alpha(1).setDuration(500).setStartDelay(200).start();
        binding.register.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(200).start();
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
        binding.openLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser()!=null){
            openMain();
        }
    }

    private void openMain() {
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();
        Toast.makeText(this, "Welcome "+binding.regName.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    private void validateData() {

        name=binding.regName.getText().toString();
        email=binding.regEmail.getText().toString();
        password=binding.regPass.getText().toString();
        if (name.isEmpty()){
            binding.regName.setError("Empty");
            binding.regName.requestFocus();
        }else if (email.isEmpty()){
            binding.regEmail.setError("Empty");
            binding.regEmail.requestFocus();
        }
        else if (password.isEmpty()){
            binding.regPass.setError("Empty");
            binding.regPass.requestFocus();
        }else{
            dialog.show();
            createUser();
        }
        
        
    }

    private void createUser() {

        auth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        uploadData();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });

    }

    private void uploadData() {
        dbref=reference.child("users");
        String key=dbref.push().getKey();
        HashMap<String,String> user=new HashMap<>();
        user.put("key",key);
        user.put("name",name);
        user.put("email",email);
        dbref.child(key).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                            openMain();
                            dialog.dismiss();
                        }else {
                            dialog.dismiss();
                            //Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                //Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}