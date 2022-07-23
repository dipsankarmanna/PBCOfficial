package com.example.collegeapp.admin.notice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.collegeapp.R;
import com.example.collegeapp.databinding.ActivityUploadNoticeBinding;
import com.example.collegeapp.notification.FcmNotificationsSender;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadNotice extends AppCompatActivity {
    ActivityUploadNoticeBinding binding;

    private Bitmap bitmap;
    DatabaseReference reference,dbref;
    StorageReference storageReference;
    String downloadUrl ="";
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUploadNoticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        getSupportActionBar().setTitle("Upload Notice");



        reference= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();
        progressDialog=new ProgressDialog(this);

       // FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);

        binding.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        binding.uploadNoticebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=binding.noticeTitle.getText().toString();
                if (title.isEmpty()){
                    binding.noticeTitle.setError("Empty");
                    binding.noticeTitle.requestFocus();
                }else if(bitmap == null){
                    uploadData();
                    //notification();
                    /*String n_title="panskura banamali college";
                    String notice=binding.noticeTitle.getText().toString();
                    if (!n_title.isEmpty()&& !notice.isEmpty()){
                        try {
                            PushNotification notification=new PushNotification(new NotificationData(n_title,notice),TOPIC);
                            sendNotification(notification);
                        } catch (Exception e) {
                            //e.printStackTrace();
                            Toast.makeText(UploadNotice.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                        }

                        /*FcmNotificationsSender notificationsSender=new FcmNotificationsSender("/topics/all",n_title,notice,
                                getApplicationContext(),UploadNotice.this);
                        notificationsSender.SendNotifications();
                    }*/
                }else {
                    uploadImage();
                    //notification();

                }
            }
        });
    }

    private void notification() {
        /*String name="Panskura Banamali College";
        String message="New notice added";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this,"n")
                .setContentText(binding.noticeTitle.getText().toString()+" uploaded successfully")
                .setSmallIcon(R.drawable.icon)
                .setAutoCancel(true)
                .setContentTitle(name);
        NotificationManagerCompat notificationCompat= NotificationManagerCompat.from(this);
        notificationCompat.notify(999,builder.build());*/

        FcmNotificationsSender notificationsSender=new FcmNotificationsSender("/topics/all","Panskura Banamali College",binding.noticeTitle.getText().toString(),
                getApplicationContext(),UploadNotice.this);
        notificationsSender.SendNotifications();

    }
/*
    private void sendNotification(PushNotification notification) {
        ApiUtilities.getClient().sendNotification(notification).enqueue(new Callback<PushNotification>() {
            @Override
            public void onResponse(Call<PushNotification> call, Response<PushNotification> response) {
                if(response.isSuccessful())
                    Toast.makeText(UploadNotice.this, "Notification send", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(UploadNotice.this, response.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PushNotification> call, Throwable t) {
                Toast.makeText(UploadNotice.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/


    private void uploadImage() {
        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filepath = storageReference.child("Notice").child(finalimg+"jpg");
        final UploadTask uploadTask=filepath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(UploadNotice.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl=String.valueOf(uri);
                                    uploadData();
                                }
                            });
                        }
                    });
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void uploadData() {
        dbref=reference.child("Notice");
        final String uniquekey = dbref.push().getKey();
        String title=binding.noticeTitle.getText().toString();
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");
        String date=currentDate.format(calendar.getTime());
        Calendar time1 = Calendar.getInstance();
        SimpleDateFormat ctime = new SimpleDateFormat("hh:mm a");
        String time=ctime.format(time1.getTime());
        NoticeData noticeData=new NoticeData(title,downloadUrl,date,time,uniquekey);
        dbref.child(uniquekey).setValue(noticeData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"Notice Uploaded Successfully",Toast.LENGTH_SHORT).show();
                notification();
                progressDialog.dismiss();
                binding.noticeTitle.setText("");
                binding.noticeImageView.setImageResource(0);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void openGallery() {
        Intent pickImage=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK){
            Uri uri=data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            binding.noticeImageView.setImageBitmap(bitmap);
        }
    }
}