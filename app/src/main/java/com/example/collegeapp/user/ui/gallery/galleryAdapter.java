package com.example.collegeapp.user.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeapp.FullImageView;
import com.example.collegeapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class galleryAdapter extends RecyclerView.Adapter<galleryAdapter.galleryViewAdapter> {

    Context context;
    List<String> images;

    public galleryAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public galleryViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.gallery_image,parent,false);

        return new galleryViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull galleryViewAdapter holder, int position) {

        try {
            Picasso.get().load(images.get(position)).into(holder.image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, FullImageView.class);
                intent.putExtra("image",images.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class galleryViewAdapter extends RecyclerView.ViewHolder {

        ImageView image;
        public galleryViewAdapter(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
        }
    }

}
