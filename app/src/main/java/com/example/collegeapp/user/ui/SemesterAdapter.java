package com.example.collegeapp.user.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeapp.DeveloperActivity;
import com.example.collegeapp.FullImageView;
import com.example.collegeapp.R;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class SemesterAdapter extends RecyclerView.Adapter<SemesterAdapter.MyViewHolder> {
    private List<SemesterModel> moviesList;
    Context context;
    public SemesterAdapter(List<SemesterModel> moviesList,Context context) {
        this.moviesList = moviesList;
        this.context=context;
    }

    @NonNull
    @Override
    public SemesterAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.semester_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SemesterAdapter.MyViewHolder holder, int position) {
        SemesterModel movie = moviesList.get(position);
        holder.icon.setImageResource(movie.getImages());

        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent=new Intent(context,AboutSemesterActivity.class);
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, ""+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;

        MyViewHolder(View view) {
            super(view);
            icon = view.findViewById(R.id.sem);
        }
    }

}