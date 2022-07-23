package com.example.collegeapp.admin.ebook;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeapp.R;
import com.example.collegeapp.admin.Admin_home_Activity;
import com.example.collegeapp.user.ebook.PdfViewerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class EbookAdapter extends RecyclerView.Adapter<EbookAdapter.EbookViewHolder> {

    ArrayList<EbookData> list;
    Context context;
    AlertDialog.Builder builder;
    String key;
    ProgressDialog dialog;

    StorageReference storageReference;
    DatabaseReference reference;

    public EbookAdapter(ArrayList<EbookData> list, Context context, String key) {
        this.list = list;
        this.context = context;
        this.key=key;
    }

    @NonNull
    @Override
    public EbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.ebook_item_layout2,parent,false);
        return new EbookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EbookViewHolder holder, int position) {
        holder.ebookName.setText(list.get(position).getPdfTitle());

        holder.ebookName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PdfViewerActivity.class);
                intent.putExtra("name",list.get(position).pdfTitle);
                intent.putExtra("pdfurl",list.get(position).getPdfUrl());
                context.startActivity(intent);
            }
        });

        holder.ebookDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder=new AlertDialog.Builder(context);
                builder.setTitle("Are you sure to delete "+list.get(position).pdfTitle+"?");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteImage();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EbookViewHolder extends RecyclerView.ViewHolder{

        TextView ebookName;
        ImageView ebookDelete;
        public EbookViewHolder(@NonNull View itemView) {
            super(itemView);
            ebookName=itemView.findViewById(R.id.ebookName);
            ebookDelete=itemView.findViewById(R.id.ebookDelete);
        }
    }
    private void deleteImage() {
        reference= FirebaseDatabase.getInstance().getReference().child("pdf");
        storageReference= FirebaseStorage.getInstance().getReference();
        dialog=new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setTitle("Deleting");
        dialog.setMessage("Please Wait...");
        dialog.show();
        reference.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                Toast.makeText(context,"Image Deleted Successfully",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(context, Admin_home_Activity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
