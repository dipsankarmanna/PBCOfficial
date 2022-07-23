package com.example.collegeapp.user.ui.faculty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.collegeapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class facultyFragment extends Fragment {

    RecyclerView csdept,bcadept,mathdept,geodept;
    LinearLayout csnodata,bcanodata,mathnodata,geonodata;
    List<TeacherData> list1,list2,list3,list4;
    TeacherAdapter adapter;
    DatabaseReference reference,dbref;
    WebView web;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_faculty, container, false);
        /*csdept=view.findViewById(R.id.csDepartment);
        bcadept=view.findViewById(R.id.bcaDepartment);
        mathdept=view.findViewById(R.id.mathDepartment);
        geodept=view.findViewById(R.id.geoDepartment);
        csnodata=view.findViewById(R.id.csNoData);
        bcanodata=view.findViewById(R.id.bcaNoData);
        mathnodata=view.findViewById(R.id.mathNoData);
        geonodata=view.findViewById(R.id.geoNoData);
        try {
            reference= FirebaseDatabase.getInstance().getReference().child("teacher");
            /*csDepartment();
            bcaDepartment();
            mathDepartment();
            geoDepartment();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        web=view.findViewById(R.id.web);
        /*web.loadUrl("https://admission.panskurabanamalicollege.org/");
        web.canGoBackOrForward(1);*/

        WebSettings webSettings=web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.loadUrl("https://admission.panskurabanamalicollege.org/");
        web.setWebViewClient(new WebViewClient());
        web.canGoBackOrForward(1);
        return view;
    }

    private void csDepartment() {
        dbref=reference.child("Computer Science");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1=new ArrayList<>();
                if (!snapshot.exists()){
                    csnodata.setVisibility(View.VISIBLE);
                    csdept.setVisibility(View.GONE);
                }else {
                    csnodata.setVisibility(View.GONE);
                    csdept.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list1.add(data);
                    }

                    csdept.setHasFixedSize(true);
                    csdept.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new TeacherAdapter(list1,facultyFragment.super.getContext());
                    csdept.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
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
                    bcanodata.setVisibility(View.VISIBLE);
                    bcadept.setVisibility(View.GONE);
                }else {
                    bcanodata.setVisibility(View.GONE);
                    bcadept.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list2.add(data);
                    }

                    bcadept.setHasFixedSize(true);
                    bcadept.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new TeacherAdapter(list2,getContext());
                    bcadept.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
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
                    mathnodata.setVisibility(View.VISIBLE);
                    mathdept.setVisibility(View.GONE);
                }else {
                    mathnodata.setVisibility(View.GONE);
                    mathdept.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list3.add(data);
                    }

                    mathdept.setHasFixedSize(true);
                    mathdept.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new TeacherAdapter(list3,getContext());
                    mathdept.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
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
                    geonodata.setVisibility(View.VISIBLE);
                    geodept.setVisibility(View.GONE);
                }else {
                    geonodata.setVisibility(View.GONE);
                    geodept.setVisibility(View.VISIBLE);
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list4.add(data);
                    }

                    geodept.setHasFixedSize(true);
                    geodept.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter=new TeacherAdapter(list4,getContext());
                    geodept.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}