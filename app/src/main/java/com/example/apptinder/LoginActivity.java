package com.example.apptinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.apptinder.DBcontext.DBcontext;
import com.example.apptinder.DBcontext.UserDao;
import com.example.apptinder.Fragment.FragmentHome;
import com.example.apptinder.Model.User;
import com.example.apptinder.SessionManager.SessionManager;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout gmail, password;

    private Button loginButton;

    private SessionManager sessionManager;

    private DBcontext db;
    private UserDao userDao;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.LoginBtn);
        gmail = findViewById(R.id.emaillogTil);
        password = findViewById(R.id.passwordlogIlt);
        db = DBcontext.getDatabase(this);
//        user = new User();
//        userDao = db.userDao();
//        user.setUsername("anh1");
//        user.setPassword( "123");
//        user.setEmail( "anh1@gmail.com");
//        userDao.insertuser(user);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String email = gmail.getEditText().getText().toString();
        String passwor = password.getEditText().getText().toString();

        userDao = db.userDao();
        user = userDao.login(email, passwor);

        if (user != null) {
            long loggedInUserId = user.getUserId();
            sessionManager = new SessionManager(getApplicationContext());
            sessionManager.loginUser((int) loggedInUserId);

            Intent intent = new Intent(LoginActivity.this, FragmentHome.class);
            startActivity(intent);
        } else {
            // Xử lý khi đăng nhập thất bại, ví dụ: hiển thị thông báo lỗi, xóa trường nhập liệu, v.v.

        }
    }


}