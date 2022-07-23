package com.example.collegeapp.user.ebook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeapp.R;

import java.util.ArrayList;

public class EbookAdapter extends RecyclerView.Adapter<EbookAdapter.EbookViewHolder> {
    ArrayList<EbookData> list;
    Context context;


    public EbookAdapter(ArrayList<EbookData> list, Context context) {

        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public EbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.ebook_item_layout,parent,false);
        return new EbookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EbookViewHolder holder, int position) {

        holder.ebookName.setText(list.get(position).getPdfTitle());

        holder.ebookName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent=new Intent(context,PdfViewerActivity.class);
                    intent.putExtra("name",list.get(position).pdfTitle);
                    intent.putExtra("pdfurl",list.get(position).getPdfUrl());
                    context.startActivity(intent);
            }
        });
        holder.ebookDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(list.get(position).getPdfUrl()));
                context.startActivity(intent);
                Toast.makeText(context,list.get(position).getPdfTitle()+" downloaded successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EbookViewHolder extends RecyclerView.ViewHolder {

        TextView ebookName;
        ImageView ebookDownload;
        public EbookViewHolder(@NonNull View itemView) {
            super(itemView);
            ebookName=itemView.findViewById(R.id.ebookName);
            ebookDownload=itemView.findViewById(R.id.ebookDownload);
        }
    }
}
