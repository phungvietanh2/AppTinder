package com.example.apptinder.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apptinder.Adapter.ListImageAdapter;
import com.example.apptinder.DBcontext.CommentDao;
import com.example.apptinder.DBcontext.DBcontext;
import com.example.apptinder.DBcontext.LikeDao;
import com.example.apptinder.DBcontext.PostDao;
import com.example.apptinder.DBcontext.UserDao;
import com.example.apptinder.Model.Comments;
import com.example.apptinder.Model.Likes;
import com.example.apptinder.Model.Post;
import com.example.apptinder.Model.User;
import com.example.apptinder.PostActivityActivity;
import com.example.apptinder.R;
import com.example.apptinder.Type.SessionManager.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FragmentSocialNetwork extends Fragment implements View.OnClickListener {
    private FloatingActionButton floatingActionButton;
    private ListImageAdapter listImageAdapter;
    private RecyclerView recyclerView;
    private DBcontext db;
    private PostDao postDao;
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5;
    private CommentDao commentDao;
    private LikeDao likeDao;
    private UserDao userDao;
    private SessionManager sessionManager;

    private boolean isLoading = false;
    private void bindingNew() {
        db = DBcontext.getDatabase(getContext());
        postDao = db.postDao();
        commentDao = db.commentDao();
        likeDao = db.likeDao();
        userDao = db.userDao();
        sessionManager = new SessionManager(getContext());
    }

    private void bindingAction() {
        floatingActionButton.setOnClickListener(this);
    }

    private void bindingData() {
        List<Post> postList = postDao.listPost();
        List<Comments> comments = commentDao.getAllComments();
        List<Likes> likes = likeDao.listLike();
        List<User> usear = userDao.getUserList();
        int usearid = sessionManager.getUserId();

        listImageAdapter = new ListImageAdapter(getContext(), postList, comments, likes, usearid,usear );

        recyclerView.setAdapter(listImageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.floating_action_button) {
            Intent intent = new Intent(getContext(), PostActivityActivity.class);
            startActivity(intent);
        }
    }

    public void loadgif() {
        Glide.with(FragmentSocialNetwork.this)
                .load(R.drawable.tag1)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView1);

        Glide.with(FragmentSocialNetwork.this)
                .load(R.drawable.cute)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView2);

        Glide.with(FragmentSocialNetwork.this)
                .load(R.drawable.gaixinh)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView3);

        Glide.with(FragmentSocialNetwork.this)
                .load(R.drawable.fa)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView4);

        Glide.with(FragmentSocialNetwork.this)
                .load(R.drawable.meo)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView5);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_social_network, container, false);

        floatingActionButton = rootView.findViewById(R.id.floating_action_button);
        recyclerView = rootView.findViewById(R.id.recyclerViewsn);
        imageView1 = rootView.findViewById(R.id.tag1);
        imageView2 = rootView.findViewById(R.id.tag2);
        imageView3 = rootView.findViewById(R.id.tag3);
        imageView4 = rootView.findViewById(R.id.tag4);
        imageView5 = rootView.findViewById(R.id.tag5);

        bindingNew();
        bindingData();
        bindingAction();
        loadgif();

        return rootView;
    }

}
