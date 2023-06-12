package com.example.apptinder.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.apptinder.Adapter.HomeAdapter;
import com.example.apptinder.DBcontext.DBcontext;
import com.example.apptinder.DBcontext.UserDao;
import com.example.apptinder.Model.User;
import com.example.apptinder.R;
import com.example.apptinder.Type.SessionManager.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentHome extends Fragment {

    private RecyclerView home;
    private TextView nameaccount, texxt ;
    private SessionManager sessionManager;
    private HomeAdapter homeAdapter;
    private UserDao userDao;
    private DBcontext db;
    private List<User> user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_home, container, false);
        home = rootView.findViewById(R.id.recyclerViewsn12);

        db = DBcontext.getDatabase(getContext());
        userDao = db.userDao();

        user = userDao.getUserList();
        homeAdapter = new HomeAdapter(getContext(),user);
        home.setAdapter(homeAdapter);
        home.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }
}