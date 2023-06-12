package com.example.apptinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class ShowsendmessgengerprofileActivity extends AppCompatActivity {
    private MessageDao messageDao;
    private SessionManager sessionManager;
    private DBcontext db;
    private ShowListChatAdapter showListChatAdapter;
    private RecyclerView recyclerView;
    private LiveData<List<Messages>> messagesLiveData;
    private LiveData<List<Conversations>> conversationsLiveData;
    private EditText text;
    private ImageView sendBtn;
    private ConversationDao conversationDao;
    private CircleImageView avata;
    private TextView namevata;
    private UserDao userDao;
    private void bindingView() {
        recyclerView = findViewById(R.id.showchatlist1);
        sendBtn = findViewById(R.id.sendBtn1);
        sessionManager = new SessionManager(this);
        text = findViewById(R.id.messageET1);
        namevata =findViewById(R.id.namevata);
        avata = findViewById(R.id.profile_image_1);

    }

    private void bindingData() {

        int id = sessionManager.getUserId();
        db = DBcontext.getDatabase(this);
        messageDao = db.messageDao();
        conversationDao = db.conversationDao();
        userDao = db.userDao();

        Intent intent = getIntent();
        int UserdetailId = intent.getIntExtra("useardetailedid", 0);
        int UserdetailId1 = intent.getIntExtra("useardetailedid1", 0);
        messagesLiveData = messageDao.getMessagesByUsers(UserdetailId, UserdetailId1);
        showListChatAdapter = new ShowListChatAdapter((Context) this, messagesLiveData);
        recyclerView.setAdapter(showListChatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<User> user = userDao.getList(UserdetailId1);
        User firstUser = user.get(0);
        String name1 = firstUser.getUsername();
        namevata.setText(name1);

        String avatar = firstUser.getAvatar();
        if (avatar != null) {
            Uri avatarUri = Uri.parse(avatar);
            avata.setImageURI(avatarUri);
        } else {
            avata.setImageResource(R.drawable.ic_close);
        }

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                String formattedDate = dateFormat.format(currentDate);
                String text_raw = text.getText().toString();
                Messages newMessage = new Messages();
                Conversations newConversation = new Conversations();

                LiveData<List<Conversations>> conversationsLiveData = conversationDao.getConversationByUsers(UserdetailId, UserdetailId1);

                newMessage.setSenderId(UserdetailId);
                newMessage.setReceiverId(UserdetailId1);
                newMessage.setContent(text_raw);
                newMessage.setTimestamp(formattedDate);
                Long messageId1 = messageDao.insertMessage(newMessage);
                text.setText("");
                conversationsLiveData.observe(ShowsendmessgengerprofileActivity.this, new Observer<List<Conversations>>() {
                    @Override
                    public void onChanged(List<Conversations> conversations) {
                        if (conversations != null && !conversations.isEmpty()) {
                            conversationDao.updateLastMessageId(UserdetailId, UserdetailId1, messageId1);
                        } else {
                            Conversations newConversation = new Conversations();
                            newConversation.setUserId1(UserdetailId);
                            newConversation.setUserId2(UserdetailId1);
                            newConversation.setLastMessageId((messageId1));
                            conversationDao.insertConversation(newConversation);
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showsendmessgengerprofile);

        bindingView();
        bindingData();
    }
}