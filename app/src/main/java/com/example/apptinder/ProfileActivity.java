package com.example.apptinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apptinder.Adapter.ShowListChatAdapter;
import com.example.apptinder.DBcontext.ConversationDao;
import com.example.apptinder.DBcontext.DBcontext;
import com.example.apptinder.DBcontext.MessageDao;
import com.example.apptinder.DBcontext.UserDao;
import com.example.apptinder.Model.Conversations;
import com.example.apptinder.Model.Messages;
import com.example.apptinder.Model.User;
import com.example.apptinder.Type.SessionManager.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SessionManager sessionManager;
    private TextView nameaccount, texxt;
    private ImageButton send, back;
    private CircleImageView profileIv;
    private DBcontext db;
    private ConversationDao conversationDao;
    private UserDao userDao;
    private User user;
    private List<User> userList;
    private MessageDao messageDao;
    private Conversations conversations;
    private LiveData<List<Conversations>> conversationsLiveData;
    private LiveData<List<Messages>> messagesLiveData;

    private EditText text;
    private ImageView sendBtn;
    private void bindingView() {
        recyclerView = findViewById(R.id.lisstprofile);
        nameaccount = findViewById(R.id.nameaccount1);
        texxt = findViewById(R.id.textprofile);
        send = findViewById(R.id.sendprofile);
        back = findViewById(R.id.backfrofile1);
        profileIv = findViewById(R.id.imgprofile);
    }

    private void bindingData() {
        sessionManager = new SessionManager(this);
        int useId = sessionManager.getUserId();
        db = DBcontext.getDatabase(this);
        conversationDao = db.conversationDao();
        userDao = db.userDao();
        User user = new User();

        Intent intent = getIntent();
        int secondUserId = intent.getIntExtra("useargetid", 0);

        userList = userDao.getList(secondUserId);

        if (!userList.isEmpty()) {
            user = userList.get(0);

            String avatar = user.getAvatar();
            try {
                if (avatar != null) {
                    Uri avatarUri = Uri.parse(avatar);
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

    private void bindingAction() {
        back.setOnClickListener(this::onclickback);
        send.setOnClickListener(this::onclicksend);
    }

    private void onclicksend(View view) {
        sessionManager = new SessionManager(this);
        int usearid = sessionManager.getUserId();

        Intent intent = getIntent();
        int secondUserId = intent.getIntExtra("useargetid", 0);

        Intent intent1 = new Intent(ProfileActivity.this, ShowsendmessgengerprofileActivity.class);
        intent1.putExtra("useardetailedid", usearid);
        intent1.putExtra("useardetailedid1", secondUserId);
        startActivity(intent1);
    }

    private void onclickback(View view) {
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bindingView();
        bindingAction();
        bindingData();

    }
}