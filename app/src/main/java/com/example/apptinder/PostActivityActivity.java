package com.example.apptinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptinder.Adapter.ImageAdapter;
import com.example.apptinder.DBcontext.DBcontext;
import com.example.apptinder.DBcontext.PostDao;
import com.example.apptinder.Model.Post;
import com.example.apptinder.Type.SessionManager.SessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostActivityActivity extends AppCompatActivity {

    private ImageView imageView;
    private RecyclerView recyclerView;
    private EditText editText;
    private Button postButton;
    private ImageButton cloes;

    private ArrayList<Uri> uriList;

    private ImageAdapter adapter;

    private static final int READ_PERMISSION_CODE = 101;
    private static final int MAX_IMAGE_LIMIT = 1;

    private DBcontext db;
    private PostDao postDao;
    private SessionManager sessionManager;

    private void bindingView() {
        imageView = findViewById(R.id.imageViewSquare);
        recyclerView = findViewById(R.id.photoRecyclerView);
        editText = findViewById(R.id.descriptionEt);
        postButton = findViewById(R.id.postButton);
        cloes = findViewById(R.id.cloex);
    }

    private void bindingAction() {
        cloes.setOnClickListener(this::onclickclose);
        postButton.setOnClickListener(this::onclickpost);
        imageView.setOnClickListener(this::onclickimage);
    }

    private void onclickimage(View view) {
        if (uriList.size() < MAX_IMAGE_LIMIT) {
            openImagePicker();
        } else {
            Toast.makeText(this, "Only 1 photo can be selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void bindingCheck() {
        //check và yêu cầu quyền
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PERMISSION_CODE);
        }
    }

    private void bindingData() {
        uriList = new ArrayList<>();
        adapter = new ImageAdapter(uriList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        recyclerView.setAdapter(adapter);

        db = DBcontext.getDatabase(this);
        postDao = db.postDao();

        sessionManager = new SessionManager(getApplicationContext());

    }

    private void onclickpost(View view) {
        savePost();
    }

    private void onclickclose(View view) {
        onBackPressed();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }


    private void savePost() {
        String content = editText.getText().toString();
        long loggedInUserId = sessionManager.getUserId();

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        String formattedDate = dateFormat.format(currentDate);

        if (content.isEmpty()) {
            Toast.makeText(this, "Please enter a description.", Toast.LENGTH_SHORT).show();
            return;
        }


        Post post = new Post();
        post.setContent(content);
        post.setUserId((int) loggedInUserId);
        List<String> imageList = new ArrayList<>();
        for (Uri uri : uriList) {
            String imageUrl = uri.toString();
            imageList.add(imageUrl);
        }
        post.setImages(imageList);
        post.setTimestamp(formattedDate);

        postDao.insertpost(post);
        Toast.makeText(this, "Post saved successfully.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PostActivityActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                if (data.getClipData() != null) {
                    int selectedCount = data.getClipData().getItemCount();
                    for (int i = 0; i < selectedCount; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        uriList.add(imageUri);
                    }
                    adapter.notifyDataSetChanged();
                } else if (data.getData() != null) {
                    Uri imageUri = data.getData();
                    uriList.add(imageUri);
                    adapter.notifyDataSetChanged();

                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_activity);
        bindingView();
        bindingAction();
        bindingCheck();
        bindingData();

    }
}
