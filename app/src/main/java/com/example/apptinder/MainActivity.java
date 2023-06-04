package com.example.apptinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.apptinder.Fragment.FragmentHome;
import com.example.apptinder.Fragment.FragmentSocialNetwork;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;

    private BottomNavigationView bottomNavigationView;


    private void bindingView() {
        bottomNavigationView = findViewById(R.id.menu);
        frameLayout = findViewById(R.id.frameLayout);
    }

    private void bindingAction() {
        bottomNavigationView.setOnItemSelectedListener(this::onItem);
    }

    private boolean onItem(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.a1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentHome()).commit();
            return true;
        }
        if (itemId == R.id.a2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentSocialNetwork()).commit();
            return true;
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindingView();
        bindingAction();

        FragmentHome fragmentHome = new FragmentHome();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragmentHome)
                .commit();
    }

}