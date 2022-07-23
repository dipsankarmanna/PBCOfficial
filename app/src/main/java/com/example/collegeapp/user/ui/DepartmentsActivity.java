package com.example.collegeapp.user.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.collegeapp.R;
import com.example.collegeapp.databinding.ActivityDepartmentsBinding;
import com.example.collegeapp.user.ui.faculty.TeacherAdapter;
import com.example.collegeapp.user.ui.faculty.TeacherData;
import com.example.collegeapp.user.ui.faculty.facultyFragment;
import com.example.collegeapp.user.ui.home.homeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DepartmentsActivity extends AppCompatActivity {
    ActivityDepartmentsBinding binding;
    RecyclerView dept;
    int img;
    String name,desc;
    private List<SemesterModel> movieList = new ArrayList<>();
    private SemesterAdapter mAdapter;
    SemesterModel movie1,movie2,movie3,movie4,movie5,movie6;
    List<TeacherData> list1,list2,list3,list4;
    TeacherAdapter adapter;
    DatabaseReference reference,dbref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDepartmentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        img=getIntent().getIntExtra("image",0);
        name=getIntent().getStringExtra("department");
        desc=getIntent().getStringExtra("departmentdesc");
        binding.deptImg.setImageResource(img);
        binding.deptName.setText(name);
        binding.aboutSemester.setText(desc);
        getSupportActionBar().hide();
        binding.lay.setTranslationY(600);
        binding.lay.setAlpha(1);
        binding.lay.animate().translationY(0).alpha(1).setDuration(500).setStartDelay(200).start();
        dept=binding.dept;
        RecyclerView recyclerView1;
        mAdapter = new SemesterAdapter(movieList,DepartmentsActivity.this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView1 =binding.semesterRecycler;
        recyclerView1.setLayoutManager(mLayoutManager);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(mAdapter);
        movie1= new SemesterModel(R.drawable.one,getApplicationContext());
        movieList.add(movie1);
        movie2= new SemesterModel(R.drawable.two,getApplicationContext());
        movieList.add(movie2);
        movie3= new SemesterModel(R.drawable.three,getApplicationContext());
        movieList.add(movie3);
        movie4= new SemesterModel(R.drawable.four,getApplicationContext());
        movieList.add(movie4);
        movie5= new SemesterModel(R.drawable.five,getApplicationContext());
        movieList.add(movie5);
        movie6= new SemesterModel(R.drawable.six,getApplicationContext());
        movieList.add(movie6);
        mAdapter.notifyDataSetChanged();
        reference= FirebaseDatabase.getInstance().getReference().child("teacher");
        department(name);

    }

    private void department(String dpname) {
        dbref=reference.child(dpname);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1=new ArrayList<>();
                if (!snapshot.exists()){
                    /*csnodata.setVisibility(View.VISIBLE);
                    csdept.setVisibility(View.GONE);*/
                }else {
                    /*csnodata.setVisibility(View.GONE);
                    csdept.setVisibility(View.VISIBLE);*/
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                        TeacherData data = dataSnapshot.getValue(TeacherData.class);
                        list1.add(data);
                    }

                    dept.setHasFixedSize(true);
                    LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
                    mLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
                    dept.setLayoutManager(mLayoutManager2);
                    adapter=new TeacherAdapter(list1, getApplicationContext());
                    dept.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}