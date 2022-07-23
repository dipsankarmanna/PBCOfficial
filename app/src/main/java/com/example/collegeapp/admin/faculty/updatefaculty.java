package com.example.collegeapp.admin.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.collegeapp.R;
import com.example.collegeapp.databinding.ActivityUpdatefacultyBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class updatefaculty extends AppCompatActivity {
    ActivityUpdatefacultyBinding binding;

    ArrayList<TeacherData> list1,list2,list3,list4;
    DatabaseReference reference,dbref;
    TeacherAdapter adapter;
    ProgressDialog progressDialog;
    String[] keys = new String[100];
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUpdatefacultyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Faculty Members'");
        //progressDialog=new ProgressDialog(this);
        //progressDialog.setMessage("Loading Teacher's Information");
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(updatefaculty.this,AddTeacher.class));
            }
        });


        try {
            reference= FirebaseDatabase.getInstance().getReference().child("teacher");
            bcaDepartment();
            csDepartment();
            mathDepartment();
            geoDepartment();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void csDepartment() {
        dbref=reference.child("Computer Science");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1=new ArrayList<>();
                if (!snapshot.exists()){
                    binding.csNoData.setVisibility(View.VISIBLE);
                    binding.csDepartment.setVisibility(View.GONE);
                }else {
                    binding.csNoData.setVisibility(View.GONE);
                    binding.csDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list1.add(data);
                    }

                    binding.csDepartment.setHasFixedSize(true);
                    binding.csDepartment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter=new TeacherAdapter(list1,updatefaculty.this,"Computer Science");
                    binding.csDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void bcaDepartment() {
        dbref=reference.child("BCA");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2=new ArrayList<>();
                if (!snapshot.exists()){
                    binding.bcaNoData.setVisibility(View.VISIBLE);
                    binding.bcaDepartment.setVisibility(View.GONE);
                }else {
                    binding.bcaNoData.setVisibility(View.GONE);
                    binding.bcaDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list2.add(data);
                    }

                    binding.bcaDepartment.setHasFixedSize(true);
                    binding.bcaDepartment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter=new TeacherAdapter(list2,updatefaculty.this,"BCA");
                    binding.bcaDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void mathDepartment() {
        dbref=reference.child("Math");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3=new ArrayList<>();
                if (!snapshot.exists()){
                    binding.mathNoData.setVisibility(View.VISIBLE);
                    binding.mathDepartment.setVisibility(View.GONE);
                }else {
                    binding.mathNoData.setVisibility(View.GONE);
                    binding.mathDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list3.add(data);
                    }

                    binding.mathDepartment.setHasFixedSize(true);
                    binding.mathDepartment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter=new TeacherAdapter(list3,updatefaculty.this,"Math");
                    binding.mathDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void geoDepartment() {
        dbref=reference.child("Geography");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list4=new ArrayList<>();
                if (!snapshot.exists()){
                    binding.geoNoData.setVisibility(View.VISIBLE);
                    binding.geoDepartment.setVisibility(View.GONE);
                }else {
                    binding.geoNoData.setVisibility(View.GONE);
                    binding.geoDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list4.add(data);
                    }

                    binding.geoDepartment.setHasFixedSize(true);
                    binding.geoDepartment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter=new TeacherAdapter(list4,updatefaculty.this,"Geography");
                    binding.geoDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}