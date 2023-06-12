package com.example.apptinder.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apptinder.Account_Setting.ListSettingAcountActivity;
import com.example.apptinder.Adapter.ListImageAdapter;
import com.example.apptinder.Adapter.ListPostProfileAdapter;
import com.example.apptinder.DBcontext.DBcontext;
import com.example.apptinder.DBcontext.PostDao;
import com.example.apptinder.DBcontext.UserDao;
import com.example.apptinder.Model.Post;
import com.example.apptinder.Model.User;
import com.example.apptinder.R;
import com.example.apptinder.Type.SessionManager.SessionManager;

import com.example.apptinder.fullscreen_imageActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentSetting extends Fragment {

    private ImageButton overflowButton;
    private CircleImageView profileIv;
    private TextView nameaccount, texxt;
    private SessionManager sessionManager;

    private RecyclerView listpost;
    private List<Post> posts;
    private DBcontext db;
    private ListPostProfileAdapter listPostProfileAdapter;
    private PostDao postDao;
    private int useId;
    private  Uri avatarUri;

    private void bindingAction() {
        overflowButton.setOnClickListener(this::onclicksetting);
        profileIv.setOnClickListener(this::onclickFullimage);
    }

    private void onclickFullimage(View view) {
        String avatarPath = avatarUri.toString(); // Chuyển đổi Uri thành String
        Intent intent = new Intent(getContext(), fullscreen_imageActivity.class);
        intent.putExtra("avatarPath", avatarPath); // Truyền đường dẫn ảnh
        startActivity(intent);
    }


    private void onclicksetting(View view) {
        Intent intent = new Intent(getContext(), ListSettingAcountActivity.class);
        startActivity(intent);

    }

    private void bindingData() {
        sessionManager = new SessionManager(getContext());
        useId = sessionManager.getUserId();
        db = DBcontext.getDatabase(getContext());
        UserDao userDao = db.userDao();

        User user = new User();
        List<User> userList = userDao.getList(useId);

        if (!userList.isEmpty()) {
            user = userList.get(0);

            String avatar = user.getAvatar();
            try {
                if (avatar != null) {
                     avatarUri = Uri.parse(avatar);
                    profileIv.setImageURI(avatarUri);
                } else {
                    profileIv.setImageResource(R.drawable.ic_close);
                }

                nameaccount.setText(user.getUsername());
                texxt.setText(user.getText());

            } catch (Exception e) {
                e.printStackTrace();
                // Xử lý nếu có lỗi khi đặt hình ảnh
            }
        }

    }

    private void bindingRecyclerView() {

        postDao = db.postDao();
        posts = postDao.listPostgetid(useId);


        listPostProfileAdapter = new ListPostProfileAdapter(getContext(), posts);
        listpost.setAdapter(listPostProfileAdapter);
        listpost.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        overflowButton = rootView.findViewById(R.id.settingIv);
        profileIv = rootView.findViewById(R.id.imageavata);
        nameaccount = rootView.findViewById(R.id.nameaccount);
        texxt = rootView.findViewById(R.id.texxt);
        listpost = rootView.findViewById(R.id.recyclerViewsn11);

        bindingAction();
        bindingData();
        bindingRecyclerView();
        return rootView;
    }


}