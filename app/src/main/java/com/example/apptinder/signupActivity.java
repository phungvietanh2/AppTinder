package com.example.apptinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptinder.DBcontext.DBcontext;
import com.example.apptinder.DBcontext.UserDao;
import com.example.apptinder.Model.User;
import com.google.android.material.textfield.TextInputLayout;

public class signupActivity extends AppCompatActivity {
    private TextInputLayout gmail, password, fullname;
    private Button sigup;
    private TextView yeslogin, checkfull, checkemail, checkpass;
    private CheckBox checkBox;
    private Intent intent;

    private void bindingView() {
        fullname = findViewById(R.id.namesigupTil);
        password = findViewById(R.id.passwordsigupIlt);
        gmail = findViewById(R.id.emaillogsigupTil);
        sigup = findViewById(R.id.sigupBtn);
        yeslogin = findViewById(R.id.noAccountTv);
        checkBox = findViewById(R.id.checkbox);
        checkfull = findViewById(R.id.nameErrorTextView);
        checkemail = findViewById(R.id.nameErrorTextView1);
        checkpass = findViewById(R.id.nameErrorTextView2);
    }

    private void bindingAction() {
        sigup.setOnClickListener(this::onclicksigup);
        yeslogin.setOnClickListener(this::oncliclaccount);
    }

    private void oncliclaccount(View view) {
        intent = new Intent(signupActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void onclicksigup(View view) {
        String email = gmail.getEditText().getText().toString();
        String passwor = password.getEditText().getText().toString();
        String fullnam = fullname.getEditText().getText().toString();

        if (fullnam.isEmpty()) {
            checkfull.setText("Vui lòng nhập tên của bạn");
            checkfull.setVisibility(View.VISIBLE);
            return;
        } else {
            checkfull.setVisibility(View.GONE);
        }

        if (email.isEmpty()) {
            checkemail.setText("Vui lòng nhập email của bạn");
            checkemail.setVisibility(View.VISIBLE);
            return;
        } else {
            checkemail.setVisibility(View.GONE);
        }

        signupActivity emailChecker = new signupActivity();
        boolean isValidEmail = emailChecker.isFptEduEmail(email);
        if (!isValidEmail) {
            checkemail.setText("Email không hợp lệ");
            checkemail.setVisibility(View.VISIBLE);
            return;
        } else {
            checkemail.setVisibility(View.GONE);
        }

        if (passwor.isEmpty()) {
            checkpass.setText("Vui lòng nhập mật khẩu của bạn");
            checkpass.setVisibility(View.VISIBLE);
            return;
        } else {
            checkpass.setVisibility(View.GONE);
        }

        if (!checkBox.isChecked()) {
            Toast.makeText(this, "Đồng ý với điều khoản và Điều kiện của LoveConnect", Toast.LENGTH_SHORT).show();
            return;
        }

        DBcontext db = DBcontext.getDatabase(getApplicationContext());
        UserDao userDao = db.userDao();

        User user = new User();
        user.setUsername(fullnam);
        user.setEmail(email);
        user.setPassword(passwor);

        userDao.insertuser(user);

        intent = new Intent(signupActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private boolean isFptEduEmail(String email) {
        String pattern = "^[A-Za-z0-9._%+-]+@fpt\\.edu\\.vn$";
        return email.matches(pattern);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        bindingView();
        bindingAction();

    }
}