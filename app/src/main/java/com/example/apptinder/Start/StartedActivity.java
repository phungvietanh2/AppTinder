package com.example.apptinder.Start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.apptinder.R;

public class StartedActivity extends AppCompatActivity {
    private Button login, signup;
    private Intent intent;

    private void bindingView(){
        login =findViewById(R.id.LoginBtn);
        signup =findViewById(R.id.registerBtn);
    }
    private void bidingAction(){
        login.setOnClickListener(this::onclicklogin);
        signup.setOnClickListener(this::onclicksigup);
    }

    private void onclicksigup(View view) {
        intent = new Intent(StartedActivity.this, signupActivity.class);
        startActivity(intent);
    }

    private void onclicklogin(View view) {
         intent = new Intent(StartedActivity.this, LoginActivity.class);
         startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started);
        bindingView();
        bidingAction();
    }
}