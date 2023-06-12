package com.example.apptinder.Account_Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apptinder.Start.LoginActivity;
import com.example.apptinder.R;

public class ListSettingAcountActivity extends AppCompatActivity {

    private TextView logout ,accountsetting;
    private ImageView back;

    private void bindingView(){
        logout = findViewById(R.id.loutoutTv);
        back = findViewById(R.id.back1);
        accountsetting = findViewById(R.id.settingname);
    }
    private void bindingAction(){
        logout.setOnClickListener(this::onclicklogout);
        back.setOnClickListener(this::onclickback);
        accountsetting.setOnClickListener(this::onclicksetting);
    }

    private void onclicksetting(View view) {
        Intent intent = new Intent(ListSettingAcountActivity.this, AcountSaveActivity.class);
        startActivity(intent);
    }

    private void onclickback(View view) {
        onBackPressed();
    }

    private void onclicklogout(View view) {
        Intent intent = new Intent(ListSettingAcountActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_setting_acount);
        bindingView();
        bindingAction();
    }
}