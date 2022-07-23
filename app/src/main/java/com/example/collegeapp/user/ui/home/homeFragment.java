package com.example.collegeapp.user.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.collegeapp.R;
import com.example.collegeapp.user.ui.MovieModel;
import com.example.collegeapp.user.ui.MoviesAdapter;
import com.example.collegeapp.user.ui.about.BranchAdapter;
import com.example.collegeapp.user.ui.about.BranchModel;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class    homeFragment extends Fragment {

    SliderLayout sliderLayout;
    ImageView map;
    ImageView email,phone;
    ViewPager viewPager;
    BranchAdapter adapter;
    List<BranchModel> list;
    int currentPage=0;
    Timer timer;
    final long DELAY_MS=300;
    final long PERIOD_MS=2000;
    private int NUM_PAGES=5;
    private List<MovieModel> movieList = new ArrayList<>();
    private MoviesAdapter mAdapter;
    MovieModel movie1,movie2,movie3,movie4,movie5,movie6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
            sliderLayout=view.findViewById(R.id.slider);
            sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
            sliderLayout.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            sliderLayout.setScrollTimeInSec(2);
            setSliderViews();
        map=view.findViewById(R.id.map);
        email=view.findViewById(R.id.email);
        phone=view.findViewById(R.id.phone);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("mailto:principal.pbc@gmail.com"));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try {
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("tel:03228252222"));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        list=new ArrayList<>();
        list.add(new BranchModel(R.drawable.ic_bca2,R.drawable.bca_dept,"BCA","The BCA course is a full time three years (six semesters) Bachelor's Degree in Computer Application."));
        list.add(new BranchModel(R.drawable.ic_cs,R.drawable.cs_dept,"Computer Science","Computer science is the study of computers and computing as well as their theoretical and practical applications."));
        list.add(new BranchModel(R.drawable.ic_math,R.drawable.bca_dept,"Mathematics","BSc Mathematics course is for three years and is often referred to as Bachelor of Science in Mathematics"));
        list.add(new BranchModel(R.drawable.ic_history,R.drawable.bca_dept,"History","BA History course is for three years and is often referred to as Bachelor of Arts in History"));
        adapter=new BranchAdapter(getContext(),list);
        /*viewPager=view.findViewById(R.id.viewPager2);
        viewPager.setAdapter(adapter);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES-1) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);*/

        //recycler of departments

        RecyclerView recyclerView1;
        mAdapter = new MoviesAdapter(movieList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(homeFragment.super.getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView1 = view.findViewById(R.id.recyler);
        recyclerView1.setLayoutManager(mLayoutManager);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        recyclerView1.setAdapter(mAdapter);
        movie1= new MovieModel(homeFragment.super.getContext(),"BCA","The BCA course is a full time three years (six semesters) Bachelor's Degree in Computer Application.",R.drawable.ic_bca2);
        movieList.add(movie1);
        movie2= new MovieModel(homeFragment.super.getContext(),"Computer Science","Computer science is the study of computers and computing as well as their theoretical and practical applications.",R.drawable.ic_cs);
        movieList.add(movie2);
        movie3= new MovieModel(homeFragment.super.getContext(),"Mathematics","BSc Mathematics course is for three years and is often referred to as Bachelor of Science in Mathematics.",R.drawable.ic_math);
        movieList.add(movie3);
        movie4= new MovieModel(homeFragment.super.getContext(),"History","BA History course is for three years and is often referred to as Bachelor of Arts in History.",R.drawable.ic_history);
        movieList.add(movie4);
        movie5= new MovieModel(homeFragment.super.getContext(),"Geography","BA Geography is a 3-years undergraduate course which is completed in 6 semesters. It deals with topics like earth and its different phenomena, features and inhabitants. It also includes the deep study of climate and soil, along with the various vegetation and plantations on the planet.",R.drawable.ic_geo);
        movieList.add(movie5);
        movie6= new MovieModel(homeFragment.super.getContext(),"Bengali","BA Geography is a 3-years undergraduate course which is completed in 6 semesters. It deals with topics like earth and its different phenomena, features and inhabitants. It also includes the deep study of climate and soil, along with the various vegetation and plantations on the planet..",R.drawable.ic_bengali);
        movieList.add(movie6);
        mAdapter.notifyDataSetChanged();
        return view;
    }

    private void openMap() {
        Uri uri=Uri.parse("geo:0, 0?q=Panskura Banamali College");
        Intent intent= new Intent(Intent.ACTION_VIEW,uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);

    }

    private void setSliderViews() {
        for (int i=0;i<5;i++){
            DefaultSliderView sliderView=new DefaultSliderView(getContext());
            switch (i){
                case 0:
                    sliderView.setImageDrawable(R.drawable.college0);
                    break;
                case 1:
                    sliderView.setImageDrawable(R.drawable.college);
                    break;
                case 2:
                    sliderView.setImageDrawable(R.drawable.college2);
                    break;
                case 3:
                    sliderView.setImageDrawable(R.drawable.college3);
                    break;
                case 4:
                    sliderView.setImageDrawable(R.drawable.college4);
                    break;
            }
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderLayout.addSliderView(sliderView);
        }
    }
}