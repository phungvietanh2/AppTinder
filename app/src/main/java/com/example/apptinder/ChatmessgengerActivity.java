//package com.example.apptinder;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.Observer;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//
//import com.example.apptinder.Adapter.ShowListChatAdapter;
//import com.example.apptinder.DBcontext.ConversationDao;
//import com.example.apptinder.DBcontext.DBcontext;
//import com.example.apptinder.DBcontext.MessageDao;
//import com.example.apptinder.Model.Conversations;
//import com.example.apptinder.Model.Messages;
//import com.example.apptinder.Type.SessionManager.SessionManager;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//public class ChatmessgengerActivity extends AppCompatActivity {
//    private MessageDao messageDao;
//    private SessionManager sessionManager;
//    private DBcontext db;
//    private ShowListChatAdapter showListChatAdapter;
//    private RecyclerView recyclerView;
//    private LiveData<List<Messages>> messagesLiveData;
//    private LiveData<List<Conversations>> conversationsLiveData;
//    private EditText text;
//    private ImageView sendBtn;
//    private ConversationDao conversationDao;
//
//    private void bindingView() {
//        recyclerView = findViewById(R.id.showchatlist);
//        sendBtn = findViewById(R.id.sendBtn);
//        sessionManager = new SessionManager(this);
//        text = findViewById(R.id.messageET);
//        int id = sessionManager.getUserId();
//        db = DBcontext.getDatabase(this);
//        messageDao = db.messageDao();
//        conversationDao = db.conversationDao();
//
//        Intent intent = getIntent();
//        int UserdetailId = intent.getIntExtra("useardetailedid", 0);
//        int UserdetailId1 = intent.getIntExtra("useardetailedid1", 0);
//        boolean isProcessed = false;
//
//        int secondUserId = intent.getIntExtra("usear2id", 0);
//
//
//        if (UserdetailId != 0 && UserdetailId1 != 0 && !isProcessed) {
//            messagesLiveData = messageDao.getMessagesByUsers(UserdetailId, UserdetailId1);
//            showListChatAdapter = new ShowListChatAdapter((Context) this, messagesLiveData);
//            recyclerView.setAdapter(showListChatAdapter);
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            isProcessed = true;
//            Log.d("1", String.valueOf(UserdetailId));
//            Log.d("2", String.valueOf(UserdetailId1));
//        }
//
//        if (secondUserId != 0 && !isProcessed) {
//
//            messagesLiveData = messageDao.getMessagesByUsers(id, secondUserId);
//            showListChatAdapter = new ShowListChatAdapter((Context) this, messagesLiveData);
//
//            recyclerView.setAdapter(showListChatAdapter);
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//            isProcessed = true;
//            Log.d("usear2id", String.valueOf(id));
//            Log.d("usear2id", String.valueOf(secondUserId));
//        }
//
//        if (isProcessed == true) {
//            sendBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Date currentDate = new Date();
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
//                    String formattedDate = dateFormat.format(currentDate);
//                    String text_raw = text.getText().toString();
//                    Messages newMessage = new Messages();
//                    Conversations conversations = new Conversations();
//
//                    // Tạo một đối tượng Messages mới
////                    if (secondUserId != 0) {
////                        LiveData<List<Conversations>> conversationsLiveData = conversationDao.getConversationByUsers(id, UserdetailId1);
//
//                        newMessage.setSenderId(id);
//                        newMessage.setReceiverId(UserdetailId1);
//                        newMessage.setContent(text_raw);
//                        newMessage.setTimestamp(formattedDate);
//                       messageDao.insertMessage(newMessage);
//                        text.setText("");
//                        Log.d("ada", "đá");
////                        conversationsLiveData.observe(ChatmessgengerActivity.this, new Observer<List<Conversations>>() {
////                        @Override
////                        public void onChanged(List<Conversations> conversations) {
////                            if (conversations != null && !conversations.isEmpty()) {
////                                // Cuộc trò chuyện đã tồn tại, thực hiện update trường lastMessageId
////                                Conversations existingConversation = conversations.get(0);
////                                existingConversation.setLastMessageId(messageId);
////                                conversationDao.updateLastMessageId(existingConversation.getUserId1(), existingConversation.getUserId2(), messageId);
////                            } else {
////                                // Cuộc trò chuyện chưa tồn tại, thực hiện insert bản ghi mới
////                                Conversations newConversation = new Conversations();
////                                newConversation.setUserId1(id);
////                                newConversation.setUserId2(UserdetailId1);
////                                newConversation.setLastMessageId(messageId);
////                                conversationDao.insertConversation(newConversation);
////                            }
////                        }
////                    });
//                }
//
//
////                    if (secondUserId == 0) {
////                        // Kiểm tra xem cuộc trò chuyện đã tồn tại trong bảng Conversations hay chưa
////                        newMessage.setSenderId(id);
////                        newMessage.setReceiverId(UserdetailId1);
////                        newMessage.setContent(text_raw);
////                        newMessage.setTimestamp(formattedDate);
////                        Long messageId = messageDao.insertMessage(newMessage);
////                        text.setText("");
////                        if (conversationsLiveData != null) {
////                            // Cuộc trò chuyện đã tồn tại, thực hiện update trường lastMessageId
////                            Conversations existingConversation = conversationsLiveData.getValue().get(0);
////                            existingConversation.setLastMessageId(messageId);
////                            conversationDao.updateLastMessageId(id,UserdetailId1,existingConversation);
////                        } else {
////                            // Cuộc trò chuyện chưa tồn tại, thực hiện insert bản ghi mới
////                            newConversation.setUserId1(id);
////                            newConversation.setUserId2(UserdetailId1);
////                            newConversation.setLastMessageId(messageId);
////                            conversationDao.insertConversation(newConversation);
////                        }
////
////                    } else {
////                        newMessage.setSenderId(id);
////                        newMessage.setReceiverId(secondUserId);
////                        newMessage.setContent(text_raw);
////                        newMessage.setTimestamp(formattedDate);
////                        Long messageId1 = messageDao.insertMessage(newMessage);
////                        text.setText("");
////
////                        newConversation.setUserId1(id);
////                        newConversation.setUserId2(secondUserId);
////                        newConversation.setLastMessageId((messageId1));
////                        conversationDao.insertConversation(newConversation);
////                    }
//
//               // }
//            });
//        }
//
//
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chatmessgenger);
//
//        bindingView();
//    }
//
//}
//
package com.example.apptinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
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
import com.example.apptinder.Model.Post;
import com.example.apptinder.Model.User;
import com.example.apptinder.Type.SessionManager.SessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatmessgengerActivity extends AppCompatActivity {
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
    private String formattedDate, text_raw;
    private int secondUserId, id;

    private TextView name;
    private CircleImageView avata;
    private UserDao userDao;

    private void bindingView() {
        recyclerView = findViewById(R.id.showchatlist);
        sendBtn = findViewById(R.id.sendBtn);
        sessionManager = new SessionManager(this);
        text = findViewById(R.id.messageET);
        name = findViewById(R.id.avatasend);
        avata = findViewById(R.id.profile_image_ssend);
    }

    private void bindingAction() {
        sendBtn.setOnClickListener(this::onclicksend);
    }

    private void bindingData() {
        db = DBcontext.getDatabase(this);
        messageDao = db.messageDao();
        conversationDao = db.conversationDao();
        userDao = db.userDao();

        Intent intent = getIntent();
        secondUserId = intent.getIntExtra("usear2id", 0);
        id = sessionManager.getUserId();

        List<User> user = userDao.getList(secondUserId);
        User firstUser = user.get(0);
        String name1 = firstUser.getUsername();
        name.setText(name1);

        String avatar = firstUser.getAvatar();
        if (avatar != null) {
            Uri avatarUri = Uri.parse(avatar);
            avata.setImageURI(avatarUri);
        } else {
            avata.setImageResource(R.drawable.ic_close);
        }

        messagesLiveData = messageDao.getMessagesByUsers(id, secondUserId);
        showListChatAdapter = new ShowListChatAdapter((Context) this, messagesLiveData);
        recyclerView.setAdapter(showListChatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Conversations newConversation = new Conversations();
    }

    private void onclicksend(View view) {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        formattedDate = dateFormat.format(currentDate);
        text_raw = text.getText().toString();
        conversationsLiveData = conversationDao.getConversationByUsers(id, secondUserId);
        Messages newMessage = new Messages();
        newMessage.setSenderId(id);
        newMessage.setReceiverId(secondUserId);
        newMessage.setContent(text_raw);
        newMessage.setTimestamp(formattedDate);
        Long messageId1 = messageDao.insertMessage(newMessage);
        text.setText("");
        conversationsLiveData.observe(ChatmessgengerActivity.this, new Observer<List<Conversations>>() {
            @Override
            public void onChanged(List<Conversations> conversations) {
                conversationDao.updateLastMessageId(id, secondUserId, messageId1);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatmessgenger);

        bindingView();
        bindingAction();
        bindingData();
    }

}