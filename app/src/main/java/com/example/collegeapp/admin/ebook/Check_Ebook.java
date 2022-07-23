package com.example.collegeapp.admin.ebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.collegeapp.databinding.ActivityCheckEbookBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Check_Ebook extends AppCompatActivity {
    ActivityCheckEbookBinding binding;
    DatabaseReference reference;
    ArrayList<EbookData> list;
    EbookAdapter adapter;
    ObjectAnimator proanm;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCheckEbookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("E-Books'");
        binding.fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Check_Ebook.this,UploadPdf.class));
            }
        });

        list=new ArrayList<>();

        getData();
    }

    private void getData() {
        reference= FirebaseDatabase.getInstance().getReference().child("pdf");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    EbookData data=dataSnapshot.getValue(EbookData.class);
                    key=dataSnapshot.getKey();
                    list.add(data);
                }
                adapter=new EbookAdapter(list,Check_Ebook.this,key);
                binding.ebookRecycler.setLayoutManager(new LinearLayoutManager(Check_Ebook.this));
                binding.ebookRecycler.setAdapter(adapter);
                binding.shimmerViewContainer.stopShimmer();
                binding.shimmerViewContainer.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Check_Ebook.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onPause() {
        binding.shimmerViewContainer.stopShimmer();
        super.onPause();
    }

    @Override
    protected void onResume() {
        binding.shimmerViewContainer.startShimmer();
        super.onResume();
    }
}