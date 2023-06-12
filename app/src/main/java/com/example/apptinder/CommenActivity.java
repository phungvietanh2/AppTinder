package com.example.apptinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptinder.Adapter.ListCommentAdapter;
import com.example.apptinder.DBcontext.CommentDao;
import com.example.apptinder.DBcontext.DBcontext;
import com.example.apptinder.DBcontext.PostDao;
import com.example.apptinder.DBcontext.UserDao;
import com.example.apptinder.Model.Comments;
import com.example.apptinder.Model.Post;
import com.example.apptinder.Model.User;
import com.example.apptinder.Type.SessionManager.SessionManager;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommenActivity extends AppCompatActivity {
    private CircleImageView avataa ,avatacmt;
    private TextView name1;
    private EditText editText, commentEt;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private ImageButton add;
    private ListCommentAdapter listCommentAdapter;
    private List<Comments> comments;
    private List<Post> posts;
    private DBcontext db;
    private CommentDao commentDao;
    private PostDao postDao;
    private SessionManager sessionManager;
    private UserDao userDao;
    private List<User> users;


    private void bindingView() {
        editText = findViewById(R.id.nameCometEt);
        imageView = findViewById(R.id.imageIv);
        recyclerView = findViewById(R.id.recyclerViewcomment);
        commentEt = findViewById(R.id.commentEditText);
        add = findViewById(R.id.addCommentButton);
        avataa = findViewById(R.id.imageViewAvatarcomen);
        name1 = findViewById(R.id.name12);
        avatacmt=findViewById(R.id.imagepreViewAvatar123);
    }


    private void bindingDataAdapter() {
        Intent intent = getIntent();
        int postId = intent.getIntExtra("postId", 0);

        db = DBcontext.getDatabase(this);

        commentDao = db.commentDao();
        postDao = db.postDao();
        userDao = db.userDao();
        sessionManager = new SessionManager(this);
        int userid1=sessionManager.getUserId();


        Post post = postDao.getPostById(postId);
        editText.setText(post.getContent());

        List<String> imageList = post.getImages();
        if (imageList != null && !imageList.isEmpty()) {
            String avatar1 = imageList.get(0); // Get the first image from the list
            if (avatar1 != null) {
                Uri avatarUri = Uri.parse(avatar1);
                imageView.setImageURI(avatarUri);
            } else {
                imageView.setImageResource(R.drawable.ic_close);
            }
        } else {
            imageView.setImageResource(R.drawable.ic_close);
        }


        int usearid = post.getUserId();

        users = userDao.getUserList();
        User user = null;

        for (User u : users) {
            if (u.getUserId() == usearid) {
                user = u;
                break;
            }
        }
        if (user != null) {

            name1.setText(user.getUsername());
            String avatar = user.getAvatar();
            if (avatar != null) {
                Uri avatarUri = Uri.parse(avatar);
                avataa.setImageURI(avatarUri);
            } else {
                avataa.setImageResource(R.drawable.ic_close);
            }
        } else {
            // Handle the case when the user list is empty
        }

        User a = userDao.getUserById(userid1);
        String av1 = a.getAvatar();
        if (av1 != null) {
            Uri avatarUri = Uri.parse(av1);
            avatacmt.setImageURI(avatarUri);
        } else {
            avataa.setImageResource(R.drawable.ic_close);
        }

Log.d("a",av1);
        comments = commentDao.getAllComments();
        posts = postDao.listPost();

        listCommentAdapter = new ListCommentAdapter(this, comments, posts, postId);

        recyclerView.setAdapter(listCommentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void bindingAction() {
        add.setOnClickListener(this::onClickadd);
    }

    private void onClickadd(View view) {
        String comment = commentEt.getText().toString();
        sessionManager = new SessionManager(getApplicationContext());
        Intent intent = getIntent();
        int postId = intent.getIntExtra("postId", 0);
        Comments comments1 = new Comments();
        comments1.setPostId(postId);
        comments1.setUserId(sessionManager.getUserId());
        comments1.setContent(comment);
        commentDao.insertcomment(comments1);
        commentEt.setText("");

        comments = commentDao.getAllComments();
        listCommentAdapter.setComments(comments);

        Toast.makeText(getApplicationContext(), "Bình luận thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        bindingView();
        bindingDataAdapter();
        bindingAction();

    }
}