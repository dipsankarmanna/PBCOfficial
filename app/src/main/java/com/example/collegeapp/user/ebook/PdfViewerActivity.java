package com.example.collegeapp.user.ebook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.collegeapp.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PdfViewerActivity extends AppCompatActivity {

    PDFView pdfView;
    WebView web;
    String url,pdfname;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        pdfname=getIntent().getStringExtra("name");
        url=getIntent().getStringExtra("pdfurl");
        dialog=new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Loading,Please wait...");
        dialog.show();
        getSupportActionBar().setTitle(pdfname);
        pdfView=findViewById(R.id.pdfView);
        new PdfDownload().execute(url);

    }

    private class PdfDownload extends AsyncTask<String,Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
           InputStream inputStream=null;
            try {
                URL url=new URL(strings[0]);
                HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode()==200){
                    inputStream=new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream)
                    .pages(0, 2, 1, 3, 3, 3)
                    .load();
            dialog.dismiss();
        }
    }
}