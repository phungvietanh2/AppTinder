package com.example.apptinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptinder.Adapter.ListCommentAdapter;
import com.example.apptinder.DBcontext.CommentDao;
import com.example.apptinder.DBcontext.DBcontext;
import com.example.apptinder.DBcontext.PostDao;
import com.example.apptinder.Model.Comments;
import com.example.apptinder.Model.Post;
import com.example.apptinder.SessionManager.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class CommenActivity extends AppCompatActivity {

    private EditText editText, commentEt;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private Button add;
    private ListCommentAdapter listCommentAdapter;
    private List<Comments> comments;
    private List<Post> posts;
    private DBcontext db;
    private CommentDao commentDao;
    private PostDao postDao;
    private SessionManager sessionManager;

    private void bindingView() {
        editText = findViewById(R.id.nameCometEt);
        imageView = findViewById(R.id.imageIv);
        recyclerView = findViewById(R.id.recyclerViewcomment);
        commentEt = findViewById(R.id.commentEditText);
        add = findViewById(R.id.addCommentButton);
    }


    private void bindingDataAdapter() {
        Intent intent = getIntent();
        if (intent != null) {
            Post post = intent.getParcelableExtra("post");
            if (post != null) {
                editText.setText(post.getContent());
                List<String> imageList = post.getImages();
                if (imageList != null && !imageList.isEmpty()) {
                    Uri imageUri = Uri.parse(imageList.get(0)); // Lấy đường dẫn Uri từ List<String>
                    imageView.setImageURI(imageUri);
                } else {
                    // Nếu danh sách ảnh rỗng, bạn có thể đặt một ảnh mặc định cho ImageView
                    imageView.setImageResource(R.drawable.ic_close);
                }
            } else {
                // Xử lý khi post là null
            }
        } else {
            // Xử lý khi intent là null
        }
        db = DBcontext.getDatabase(this);

        commentDao = db.commentDao();
        postDao = db.postDao();

        comments = commentDao.getAllComments();
        posts = postDao.listPost();
        int postId = intent.getIntExtra("postId", 0);
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

        // Cập nhật danh sách comment và thông báo cho adapter biết rằng dữ liệu đã thay đổi
        comments = commentDao.getAllComments();
        listCommentAdapter.setComments(comments);
        // Hiển thị thông báo bình luận thành công
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