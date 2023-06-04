package com.example.apptinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView view;

    private void bindingView() {
        loginButton = findViewById(R.id.LoginBtn);
        gmail = findViewById(R.id.emaillogTil);
        password = findViewById(R.id.passwordlogIlt);
        view =findViewById(R.id.AccountTv);
    }

    private void bindingAction() {
        loginButton.setOnClickListener(this::onclicklogin);
        view.setOnClickListener(this::onclicksigup);

    }

    private void onclicksigup(View view) {
        Intent intent = new Intent(LoginActivity.this,signupActivity.class);
        startActivity(intent);
    }

    private void onclicklogin(View view) {
        String email = gmail.getEditText().getText().toString();
        String passwor = password.getEditText().getText().toString();
        db = DBcontext.getDatabase(this);
        userDao = db.userDao();
        user = userDao.login(email, passwor);

        if (user != null) {
            long loggedInUserId = user.getUserId();
            sessionManager = new SessionManager(getApplicationContext());
            sessionManager.loginUser((int) loggedInUserId);

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Tài khoản và mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindingView();
        bindingAction();
    }


}