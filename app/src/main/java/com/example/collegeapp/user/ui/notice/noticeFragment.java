package com.example.collegeapp.user.ui.notice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.collegeapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class noticeFragment extends Fragment {

    RecyclerView noticeRecyclerview;
    ProgressBar progressBar;
    ArrayList<NoticeData> list;
    noticeAdapter adapter;
    DatabaseReference reference;
    FloatingActionButton chat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_notice, container, false);

        noticeRecyclerview=view.findViewById(R.id.noticeRecyclerview);
        progressBar=view.findViewById(R.id.progressBar);
        reference= FirebaseDatabase.getInstance().getReference().child("Notice");
        noticeRecyclerview.setLayoutManager(new LinearLayoutManager(noticeFragment.super.getContext()));
        noticeRecyclerview.setHasFixedSize(true);
        chat=view.findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(noticeFragment.super.getContext(), "work in progress", Toast.LENGTH_SHORT).show();
            }
        });
        getNotice();

        return view;
    }

    private void getNotice() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    NoticeData data=dataSnapshot.getValue(NoticeData.class);
                    list.add(0,data);
                }
                adapter=new noticeAdapter(noticeFragment.super.getContext(),list);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                noticeRecyclerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(noticeFragment.super.getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}