package com.example.apptinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.apptinder.Account_Setting.ListSettingAcountActivity;

public class fullscreen_imageActivity extends AppCompatActivity {
    private ImageView full ,back;

    private void bindingView(){
        full = findViewById(R.id.imagfullscrem);
        back= findViewById(R.id.closeIcon);
    }
    private void bindingAction(){
        back.setOnClickListener(this::onclickback);
    }

    private void onclickback(View view) {
      onBackPressed();
    }

    private void bindingData(){
        Intent intent = getIntent();
        String avatarPath = intent.getStringExtra("avatarPath");
        Uri avatarUri = Uri.parse(avatarPath);

        full.setImageURI(avatarUri);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_image);
        bindingView();
        bindingAction();
        bindingData();
    }
}