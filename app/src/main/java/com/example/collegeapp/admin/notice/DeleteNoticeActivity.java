package com.example.collegeapp.admin.notice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.collegeapp.R;
import com.example.collegeapp.databinding.ActivityDeleteNoticeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteNoticeActivity extends AppCompatActivity {
    ActivityDeleteNoticeBinding binding;

    ArrayList<NoticeData> list;
    noticeAdapter adapter;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDeleteNoticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Verify Notice");
        reference= FirebaseDatabase.getInstance().getReference().child("Notice");
        binding.deleteNoticeRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.deleteNoticeRecyclerview.setHasFixedSize(true);
        getNotice();
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
                adapter=new noticeAdapter(DeleteNoticeActivity.this,list);
                adapter.notifyDataSetChanged();
                binding.progressBar.setVisibility(View.GONE);
                binding.deleteNoticeRecyclerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(DeleteNoticeActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}