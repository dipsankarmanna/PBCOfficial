package com.example.collegeapp.user.ui.gallery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.collegeapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class galleryFragment extends Fragment {

    RecyclerView independence,others;
    galleryAdapter adapter;
    DatabaseReference reference;
    ProgressBar progressBar;
    LinearLayout gal;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View view= inflater.inflate(R.layout.fragment_gallery, container, false);
        
        independence=view.findViewById(R.id.independence);
        others=view.findViewById(R.id.others);
        progressBar=view.findViewById(R.id.progressbar);
        gal=view.findViewById(R.id.gal);
        reference= FirebaseDatabase.getInstance().getReference().child("gallery");
        
        getIndependence();
        getOthers();
        
        return view;
    }

    private void getOthers() {
        reference.child("Others").addValueEventListener(new ValueEventListener() {

            List<String> imageList=new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String data= (String) dataSnapshot.getValue();
                    imageList.add(data);
                }

                adapter=new galleryAdapter(getContext(),imageList);
                others.setLayoutManager(new GridLayoutManager(getContext(),3));
                others.setAdapter(adapter);
                gal.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                gal.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getIndependence() {
        reference.child("Independence Day").addValueEventListener(new ValueEventListener() {

            List<String> imageList=new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String data= (String) dataSnapshot.getValue();
                    imageList.add(data);
                }

                adapter=new galleryAdapter(getContext(),imageList);
                independence.setLayoutManager(new GridLayoutManager(getContext(),3));
                independence.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}