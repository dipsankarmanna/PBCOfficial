package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.collegeapp.user.authentication.LoginActivity;
import com.example.collegeapp.databinding.ActivityMainBinding;
import com.example.collegeapp.user.ebook.EbookActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding binding;
    AppBarConfiguration mAppBarConfiguration;
   // NavController navController;
    ActionBarDrawerToggle toggle;
    //SNavigationDrawer toggle;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int chekedItem;
    String selected;
    final String CHEKEDITEM="cheked_item";
    FirebaseAuth auth;
    AlertDialog.Builder builder;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.bottonNavigationView.setTranslationX(800);
        binding.bottonNavigationView.setAlpha(1);
        binding.lay.setTranslationY(800);
        binding.lay.setAlpha(1);
        binding.bottonNavigationView.animate().translationX(0).alpha(1).setDuration(500).setStartDelay(800).start();
        binding.lay.animate().translationY(0).alpha(1).setDuration(500).setStartDelay(400).start();
        auth=FirebaseAuth.getInstance();

        sharedPreferences=this.getSharedPreferences("themes", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();


        try {
            switch (getChekedItem()){
                case 0:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    break;
                case 1:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
                case 2:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        toggle=new ActionBarDrawerToggle(this,binding.drawerLayout,null,R.string.start,R.string.close);
            binding.drawerLayout.addDrawerListener(toggle);
            toggle.syncState();

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            binding.navigationView.setNavigationItemSelectedListener(this); /*{
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.navigation_ebook:
                            Toast.makeText(MainActivity.this, "ebook", Toast.LENGTH_SHORT).show();
                            binding.drawerLayout.closeDrawer(GravityCompat.START);
                            break;
                    }
                    return true;
                }
            });*/

        NavController navController = Navigation.findNavController(this, R.id.frame_layout);
        NavigationUI.setupWithNavController(binding.bottonNavigationView, navController);


        /*NavController navController2 = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController2, mAppBarConfiguration);
        NavigationUI.setupWithNavController(binding.navigationView, navController2);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        if (item.getItemId()==R.id.logout){
            builder=new AlertDialog.Builder(this);
            builder.setTitle("Are You Sure to Log Out?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialog=new ProgressDialog(MainActivity.this);
                    dialog.setCancelable(false);
                    dialog.setMessage("Logging Out...");
                    dialog.show();
                    auth.signOut();
                    dialog.dismiss();
                    openLogin();
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
        else if (item.getItemId()==R.id.language){
            Toast.makeText(this, "work in progress", Toast.LENGTH_SHORT).show();
        }
        return true;
    }


    private void openLogin() {
        startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser()==null){
            openLogin();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_ebook:
                startActivity(new Intent(this, EbookActivity.class));
                break;

            case R.id.navigation_color:
                showDialog();
                onBackPressed();
                break;
            case R.id.navigation_video:
                Uri uri= Uri.parse("https://youtu.be/tFze0e8CSHc");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
                onBackPressed();
                break;
            case R.id.navigation_wesite:
                Uri uri1= Uri.parse("https://panskurabanamalicollege.org/");
                startActivity(new Intent(Intent.ACTION_VIEW,uri1));
                onBackPressed();
                break;
            case R.id.navigation_developer:
                startActivity(new Intent(getApplicationContext(),DeveloperActivity.class));
                onBackPressed();
                break;
            case R.id.navigation_share:
                try {
                    ApplicationInfo api=getApplicationContext().getApplicationInfo();
                    String apkpath=api.sourceDir;
                    Intent intent=new Intent(Intent.ACTION_SEND);
                    intent.setType("application/vnd.android.package-archive");
                    intent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(new File(apkpath)));
                    startActivity(Intent.createChooser(intent,"ShareVia"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }

        return true;
    }

    private void showDialog() {

        String[] themes=this.getResources().getStringArray(R.array.theme);

        MaterialAlertDialogBuilder builder=new MaterialAlertDialogBuilder(this);
        builder.setTitle("Selected Theme");
        builder.setSingleChoiceItems(R.array.theme, getChekedItem(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                selected=themes[i];
                chekedItem=i;

            }
        });

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        if (selected==null){
                            selected=themes[i];
                            chekedItem=i;
                        }
                        switch (selected){
                            case "System Default":
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                                break;
                            case "Dark":
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                                break;
                            case "Light":
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                                break;
                        }
                        setChekedItem(chekedItem);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

        AlertDialog dialog=builder.create();
        dialog.show();
    }

    private int getChekedItem(){
        return sharedPreferences.getInt(CHEKEDITEM,0);
    }

    private void setChekedItem(int i){
        try {
            editor.putInt(CHEKEDITEM,i);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }

    /*
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }*/
}