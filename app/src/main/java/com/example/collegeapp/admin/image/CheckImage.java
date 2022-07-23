package com.example.collegeapp.admin.image;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.collegeapp.databinding.ActivityCheckImageBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CheckImage extends AppCompatActivity {
    ActivityCheckImageBinding binding;

    galleryAdapter adapter;
    DatabaseReference reference;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCheckImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Gallery");
        binding.fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CheckImage.this,UploadImage.class));
            }
        });

        reference= FirebaseDatabase.getInstance().getReference().child("gallery");

        getIndependence();
        getOthers();

    }

    private void getOthers() {
        reference.child("Others").addValueEventListener(new ValueEventListener() {

            List<String> imageList=new ArrayList<>();
            List<String> imageList2=new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String data= (String) dataSnapshot.getValue();
                    String key=dataSnapshot.getKey();
                    imageList.add(data);
                    imageList2.add(key);
                }

                adapter=new galleryAdapter(CheckImage.this,imageList,imageList2,"Others");
                binding.others.setLayoutManager(new GridLayoutManager(CheckImage.this,3));
                binding.others.setAdapter(adapter);
                binding.gal.setVisibility(View.VISIBLE);
                binding.progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.gal.setVisibility(View.VISIBLE);
                binding.progressbar.setVisibility(View.GONE);
            }
        });
    }

    private void getIndependence() {
        reference.child("Independence Day").addValueEventListener(new ValueEventListener() {

            List<String> imageList1=new ArrayList<>();
            List<String> imageList3=new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    try {
                        String data= (String) dataSnapshot.getValue();
                        String key=dataSnapshot.getKey();
                        imageList1.add(data);
                        imageList3.add(key);
                    } catch (Exception e) {
                        Toast.makeText(CheckImage.this, ""+e.toString(), Toast.LENGTH_SHORT).show();                    }
                }

                adapter=new galleryAdapter(CheckImage.this,imageList1,imageList3,"Independence Day");
                binding.independence.setLayoutManager(new GridLayoutManager(CheckImage.this,3));
                binding.independence.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(CheckImage.this, ""+error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}