package com.example.collegeapp.admin.faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.collegeapp.R;
import com.example.collegeapp.databinding.ActivityUpdateTeacherBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class updateTeacherActivity extends AppCompatActivity {
    ActivityUpdateTeacherBinding binding;

    String name,email,image,post;
    Bitmap bitmap=null;
    StorageReference storageReference;
    DatabaseReference reference;
    String downloadurl;
    String uniquekey,category;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUpdateTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog=new ProgressDialog(this);
        dialog.setCancelable(false);
        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        post=getIntent().getStringExtra("post");
        image=getIntent().getStringExtra("image");
        uniquekey=getIntent().getStringExtra("key");
        category=getIntent().getStringExtra("category");

        getSupportActionBar().setTitle("Teacher's Information");

        reference= FirebaseDatabase.getInstance().getReference().child("teacher");
        storageReference= FirebaseStorage.getInstance().getReference();
        try {
            Picasso.get().load(image).into(binding.updateTeacherImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.updateTeacherName.setText(name);
        binding.updateTeacherEmail.setText(email);
        binding.updateTeacherPost.setText(post);
        binding.updateTeacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        binding.updateTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setTitle("Updating");
                dialog.setMessage("Please Wait...");
                dialog.show();
                checkValidation();
            }
        });
        binding.deleteTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });
    }

    private void deleteData() {
        dialog.setTitle("Deleting");
        dialog.setMessage("Please Wait...");
        dialog.show();
        reference.child(category).child(uniquekey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),"Information Deleted Successfully",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(),updatefaculty.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkValidation() {
        name=binding.updateTeacherName.getText().toString();
        email=binding.updateTeacherEmail.getText().toString();
        post=binding.updateTeacherPost.getText().toString();

        if (name.isEmpty()){
            binding.updateTeacherName.setError("Empty");
            binding.updateTeacherName.requestFocus();
        }else if (email.isEmpty()){
            binding.updateTeacherEmail.setError("Empty");
            binding.updateTeacherEmail.requestFocus();
        }else if (post.isEmpty()){
            binding.updateTeacherPost.setError("Empty");
            binding.updateTeacherPost.requestFocus();
        } else if (bitmap==null){
            updateData(image);
        }else {
            uploadImage();
        }
    }

    private void updateData(String s) {
        HashMap hp = new HashMap();
        hp.put("name",name);
        hp.put("email",email);
        hp.put("image",s);
        hp.put("post",post);
        reference.child(category).child(uniquekey).updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),"Information Updated Successfully",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getApplicationContext(),updatefaculty.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filepath = storageReference.child("Teachers").child(finalimg+"jpg");
        final UploadTask uploadTask=filepath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(updateTeacherActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadurl=String.valueOf(uri);
                                    try {
                                        updateData(downloadurl);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                }else {
                    //progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            binding.updateTeacherImage.setImageBitmap(bitmap);
        }
    }

}