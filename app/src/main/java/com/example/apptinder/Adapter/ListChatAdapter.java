package com.example.apptinder.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.os.MessageCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apptinder.ChatmessgengerActivity;
import com.example.apptinder.DBcontext.ConversationDao;
import com.example.apptinder.DBcontext.DBcontext;
import com.example.apptinder.DBcontext.MessageDao;
import com.example.apptinder.DBcontext.UserDao;
import com.example.apptinder.Model.Conversations;
import com.example.apptinder.Model.Messages;
import com.example.apptinder.Model.User;
import com.example.apptinder.R;
import com.example.apptinder.Type.SessionManager.SessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListChatAdapter extends RecyclerView.Adapter<ListChatAdapter.ViewHolder> {

    private LiveData<List<Messages>> messagesLiveData;
    private Context context;
    private int usearid;
    private DBcontext db;
    private MessageDao messageDao;
    private Set<Integer> uniqueUserIds;
    private LiveData<List<Conversations>> conversationsLiveData;
    private Set<Integer> uniqueConversationIds = new HashSet<>();
    private List<User> users;
    private UserDao userDao;
    private ConversationDao conversationDao;

    public ListChatAdapter(Context context, LiveData<List<Conversations>> conversationsLiveData, List<User> users, LiveData<List<Messages>> messagesLiveData) {
        this.context = context;
        this.conversationsLiveData = conversationsLiveData;
        this.users = users;
        this.messagesLiveData = messagesLiveData;


        // Lắng nghe sự thay đổi trong conversationsLiveData
        conversationsLiveData.observeForever(new Observer<List<Conversations>>() {
            @Override
            public void onChanged(List<Conversations> conversations) {

                notifyDataSetChanged();
            }
        });
        messagesLiveData.observeForever(new Observer<List<Messages>>() {
            @Override
            public void onChanged(List<Messages> messages) {
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        db = DBcontext.getDatabase(context);
        messageDao = db.messageDao();
        userDao = db.userDao();

        conversationDao = db.conversationDao();

        List<Conversations> conversations = conversationsLiveData.getValue();
        if (conversations != null && position < conversations.size()) {
            Conversations conversation = conversations.get(position);
            int userId1 = conversation.getUserId1();
            int userId2 = conversation.getUserId2();
            SessionManager sessionManager = new SessionManager(context);
            int loggedInUserId = sessionManager.getUserId();

            int secondUserId = (userId1 == loggedInUserId) ? userId2 : userId1;

            User currentUser = null;
            for (User a : users) {
                if (a.getUserId() == secondUserId) {
                    currentUser = a;
                    break;
                }
            }

            if (currentUser != null) {
                String avatar = currentUser.getAvatar();

                if (avatar != null) {
                    Uri avatarUri = Uri.parse(avatar);
                    holder.view.setImageURI(avatarUri);
                } else {
                    holder.view.setImageResource(R.drawable.ic_close);
                }
                String username = currentUser.getUsername();

                String messageText = username;
                String avata1 =(avatar);

                holder.message.setText(messageText);

                Long lastMessageId = conversation.getLastMessageId();

                List<Messages> messages = messagesLiveData.getValue();
                if (messages != null) {
                    for (Messages message : messages) {
                        if (message.getMessageId() == lastMessageId) {
                            holder.chatould.setText(message.getContent());
                            break;
                        }
                    }
                } else {
                    holder.chatould.setText("");
                }



                holder.message.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ChatmessgengerActivity.class);
                        intent.putExtra("usear2id", secondUserId);
//                        intent.putExtra("name1", messageText);
                        context.startActivity(intent);
                    }
                });
            }
        }


    }


    @Override
    public int getItemCount() {
        List<Conversations> conversations = conversationsLiveData.getValue();
        if (conversations != null) {
            return conversations.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView message, chatould;
        private CircleImageView view;
        public ImageView deleteIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.mess1);
            view = itemView.findViewById(R.id.avatachat);
            chatould = itemView.findViewById(R.id.chatlisstout);
        }
    }

    private ArrayList<Uri> convertUriList(List<String> imageList) {
        ArrayList<Uri> uriList = new ArrayList<>();
        for (String imageUrl : imageList) {
            Uri uri = Uri.parse(imageUrl);
            uriList.add(uri);
        }
        return uriList;
    }

    public void deleteMessage(int position) {
        List<Conversations> conversations = conversationsLiveData.getValue();
        if (conversations != null && position < conversations.size()) {
            Conversations conversation = conversations.get(position);
            int messageId = conversation.getUserId1();
            int messageId1 = conversation.getUserId1();
            // Xóa tin nhắn
            messageDao.deleteMessagesender(Math.toIntExact(messageId));


            SessionManager sessionManager = new SessionManager(context);
            int loggedInUserId = sessionManager.getUserId();
            if (conversation.getUserId1() == loggedInUserId || conversation.getUserId2() == loggedInUserId) {
                conversationDao.deleteConversation(conversation);
            }

            notifyDataSetChanged();
        }
    }

}
