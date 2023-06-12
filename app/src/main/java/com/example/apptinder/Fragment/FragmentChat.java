package com.example.apptinder.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apptinder.Adapter.ListChatAdapter;
import com.example.apptinder.Adapter.ShowListChatAdapter;
import com.example.apptinder.DBcontext.ConversationDao;
import com.example.apptinder.DBcontext.DBcontext;
import com.example.apptinder.DBcontext.MessageDao;
import com.example.apptinder.DBcontext.UserDao;
import com.example.apptinder.Model.Conversations;
import com.example.apptinder.Model.Messages;
import com.example.apptinder.Model.User;
import com.example.apptinder.R;
import com.example.apptinder.Type.SessionManager.SessionManager;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentChat extends Fragment {
    private DBcontext db;
    private MessageDao messageDao;
    private ListChatAdapter listChatAdapter;
    private SessionManager sessionManager;
    private RecyclerView recyclerView;
    private ConversationDao conversationDao;
    private LiveData<List<Conversations>> conversationsLiveData;
    private LiveData<List<Messages>> messagesLiveData;
    private List<User> users;
    private UserDao userDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.listchat1);
        db = DBcontext.getDatabase(getContext());
        userDao = db.userDao();
        messageDao = db.messageDao();
        Messages messages = new Messages();

        // Lấy ngày giờ hiện tại
        Date currentDate = new Date();

        // Định dạng ngày giờ
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(currentDate);


        conversationDao = db.conversationDao();
        sessionManager = new SessionManager(getContext());
        int usearid = sessionManager.getUserId();

        messagesLiveData = messageDao.getAllMessages();

        //conversationsLiveData = conversationDao.getLatestConversationWithMessage(usearid);

        users = userDao.getUserList();
        conversationsLiveData = conversationDao.getConversationsWithLatestMessage1(usearid);


        listChatAdapter = new ListChatAdapter(getContext(), conversationsLiveData, users, messagesLiveData);

        recyclerView.setAdapter(listChatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn có muốn xóa?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                listChatAdapter.deleteMessage(position);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Xử lý khi người dùng chọn "No"
                                listChatAdapter.notifyItemChanged(position);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        };



        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        return view;

    }
}