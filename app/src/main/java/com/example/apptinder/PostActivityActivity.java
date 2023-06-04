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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptinder.Adapter.ImageAdapter;
import com.example.apptinder.DBcontext.DBcontext;
import com.example.apptinder.DBcontext.PostDao;
import com.example.apptinder.Model.Post;
import com.example.apptinder.SessionManager.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class PostActivityActivity extends AppCompatActivity {

    private ImageView imageView;
    private RecyclerView recyclerView;
    private EditText editText;
    private Button postButton;
    private TextView totalPhotosTextView;

    private ArrayList<Uri> uriList;

    private ImageAdapter adapter;

    private static final int READ_PERMISSION_CODE = 101;
    private static final int MAX_IMAGE_LIMIT = 9;

    private DBcontext db;
    private PostDao postDao;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_activity);

        imageView = findViewById(R.id.imageViewSquare);
        recyclerView = findViewById(R.id.photoRecyclerView);
        editText = findViewById(R.id.descriptionEt);
        postButton = findViewById(R.id.postButton);
        totalPhotosTextView = findViewById(R.id.toatalp);

        uriList = new ArrayList<>();
        adapter = new ImageAdapter(uriList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);

        db = DBcontext.getDatabase(this);
        postDao = db.postDao();
        sessionManager = new SessionManager(getApplicationContext());

        // Check and request READ_EXTERNAL_STORAGE permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PERMISSION_CODE);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uriList.size() < MAX_IMAGE_LIMIT) {
                    openImagePicker();
                } else {
                    Toast.makeText(PostActivityActivity.this, "You have reached the maximum limit of 9 images.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePost();
            }
        });
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

        if (content.isEmpty()) {
            Toast.makeText(this, "Please enter a description.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (uriList.isEmpty()) {
            Toast.makeText(this, "Please select at least one image.", Toast.LENGTH_SHORT).show();
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

        postDao.insertpost(post);
        Toast.makeText(this, "Post saved successfully.", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                if (data.getClipData() != null) {
                    int selectedCount = data.getClipData().getItemCount();
                    int totalSelected = uriList.size() + selectedCount;

                    if (totalSelected > MAX_IMAGE_LIMIT) {
                        Toast.makeText(this, "You can only select up to 9 images.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    for (int i = 0; i < selectedCount; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        uriList.add(imageUri);
                    }
                    adapter.notifyDataSetChanged();
                    totalPhotosTextView.setText("Photos (" + uriList.size() + ")");
                } else if (data.getData() != null) {
                    Uri imageUri = data.getData();

                    if (uriList.size() >= MAX_IMAGE_LIMIT) {
                        Toast.makeText(this, "You have reached the maximum limit of 9 images.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    uriList.add(imageUri);
                    adapter.notifyDataSetChanged();
                    totalPhotosTextView.setText("Photos (" + uriList.size() + ")");
                }
            }
        }
    }
}
