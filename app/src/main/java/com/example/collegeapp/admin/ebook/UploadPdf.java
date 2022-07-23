package com.example.collegeapp.admin.ebook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Toast;

import com.example.collegeapp.databinding.ActivityUploadPdfBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;

public class UploadPdf extends AppCompatActivity {
    ActivityUploadPdfBinding binding;

    private Uri pdfData;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String downloadUrl ="";
    ProgressDialog progressDialog;
    String pdfname,title;
    String[] ebook_category = new String[]{"Computer Books","Bengali","English","Mathematics","History","Geography"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUploadPdfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Upload E-Book");
        databaseReference= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        binding.addPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        binding.uploadPdfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title=binding.pdfTitle.getText().toString();
                if (title.isEmpty()){
                    binding.pdfTitle.setError("Empty");
                    binding.pdfTitle.requestFocus();
                }else if (pdfData ==null){
                    Toast.makeText(UploadPdf.this,"Please Select any Pdf",Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadpdf();
                }
            }
        });
    }

    private void uploadpdf() {
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Uploading Pdf");
        progressDialog.show();
        StorageReference reference=storageReference.child("pdf/"+ pdfname+"-"+System.currentTimeMillis()+".pdf");
        reference.putFile(pdfData)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri=uriTask.getResult();
                        uploadData(String.valueOf(uri));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadPdf.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void uploadData(String downloadUrl) {
        String uniquekey=databaseReference.child("pdf").push().getKey();

        HashMap data = new HashMap();
        data.put("pdfTitle",title);
        data.put("pdfUrl",downloadUrl);
        databaseReference.child("pdf").child(uniquekey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(UploadPdf.this,"E-Book Uploaded Successfully",Toast.LENGTH_SHORT).show();
                binding.pdfTitle.setText("");
                binding.pdfTextView.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(UploadPdf.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void openGallery() {
        Intent intent=new Intent();
        intent.setType("pdf/docs/ppt");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pdf File"),1);
        Toast.makeText(this, "Select Pdf file", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK){
            pdfData=data.getData();
            if (pdfData.toString().startsWith("content://")){
                Cursor cursor=null;
                try {
                    cursor=UploadPdf.this.getContentResolver().query(pdfData,null,null,null,null);
                    if (cursor != null && cursor.moveToFirst()){
                        pdfname=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if (pdfData.toString().startsWith("file://")){
                pdfname=new File(pdfData.toString()).getName();
            }
            binding.pdfTextView.setText(pdfname);
        }
    }

}