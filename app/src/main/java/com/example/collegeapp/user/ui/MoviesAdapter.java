package com.example.collegeapp.user.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.collegeapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
    private List<MovieModel> moviesList;


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView branch_name,branch_desc;
        ImageView icon;
        Context context;
        CardView card;

        MyViewHolder(View view) {
            super(view);
            icon = view.findViewById(R.id.branch_icon);
            branch_name = view.findViewById(R.id.branch_title);
            branch_desc = view.findViewById(R.id.branch_desc);
            card = view.findViewById(R.id.cb);
        }
    }

    public MoviesAdapter(List<MovieModel> moviesList) {
        this.moviesList = moviesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.branch_item_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MovieModel movie = moviesList.get(position);
        //holder.p_img.setImageResource(movie.getImg());
        holder.branch_name.setText(movie.getBranch_name());
        holder.branch_desc.setText(movie.getBranch_desc());
        holder.context = movie.getContext();
        holder.icon.setImageResource(movie.getImg());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next(holder,movie,position);
            }
        });
        holder.branch_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next(holder,movie,position);
            }
        });
    }

    private void next(MyViewHolder holder,MovieModel movie,int position) {
        int[] images={R.drawable.bca_dept,R.drawable.compsc,R.drawable.math2,R.drawable.historyimg,R.drawable.geography,R.drawable.bengali};
        Intent intent=new Intent(holder.context,DepartmentsActivity.class);
        intent.putExtra("image",images[position]);
        intent.putExtra("department",movie.getBranch_name());
        intent.putExtra("departmentdesc",movie.getBranch_desc());
        holder.context.startActivity(intent);

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}