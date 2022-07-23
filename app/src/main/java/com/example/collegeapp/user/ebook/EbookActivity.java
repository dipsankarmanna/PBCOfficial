package com.example.collegeapp.user.ebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.collegeapp.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EbookActivity extends AppCompatActivity {

    RecyclerView ebookRecycler;
    DatabaseReference reference;
    ArrayList<EbookData> list;
    EbookAdapter adapter;
    ObjectAnimator proanm;
    ShimmerFrameLayout shimmer_view_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);
        ebookRecycler=findViewById(R.id.ebookRecycler);
        shimmer_view_container=findViewById(R.id.shimmer_view_container);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("E-Books");
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
                    list.add(data);
                }
                adapter=new EbookAdapter(list,EbookActivity.this);
                ebookRecycler.setLayoutManager(new LinearLayoutManager(EbookActivity.this));
                ebookRecycler.setAdapter(adapter);
                shimmer_view_container.stopShimmer();
                shimmer_view_container.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onPause() {
        shimmer_view_container.stopShimmer();
        super.onPause();
    }

    @Override
    protected void onResume() {
        shimmer_view_container.startShimmer();
        super.onResume();
    }
}