package com.example.apptinder.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptinder.Model.Conversations;
import com.example.apptinder.Model.Messages;
import com.example.apptinder.R;
import com.example.apptinder.Type.SessionManager.SessionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class ShowListChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_SENDER = 0;
    private static final int VIEW_TYPE_RECEIVER = 1;

    private LiveData<List<Messages>> messagesLiveData;
    private Context context;

    public ShowListChatAdapter(Context context, LiveData<List<Messages>> messagesLiveData) {
        this.context = context;
        this.messagesLiveData = messagesLiveData;

        messagesLiveData.observeForever(new Observer<List<Messages>>() {
            @Override
            public void onChanged(List<Messages> messages) {
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENDER) {
            View senderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.showchat, parent, false);
            return new SenderViewHolder(senderView);
        } else if (viewType == VIEW_TYPE_RECEIVER) {
            View receiverView = LayoutInflater.from(parent.getContext()).inflate(R.layout.receiver_chat_item, parent, false);
            return new ReceiverViewHolder(receiverView);
        }

        throw new IllegalArgumentException("Invalid view type");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        List<Messages> messages = messagesLiveData.getValue();

        if (messages != null && position < messages.size()) {
            Messages message = messages.get(position);

            if (holder instanceof SenderViewHolder) {
                SenderViewHolder senderViewHolder = (SenderViewHolder) holder;
                senderViewHolder.messageTextView.setText(message.getContent());
            } else if (holder instanceof ReceiverViewHolder) {
                ReceiverViewHolder receiverViewHolder = (ReceiverViewHolder) holder;
                receiverViewHolder.messageTextView.setText(message.getContent());
            }
        }
    }

    @Override
    public int getItemCount() {
        List<Messages> messages = messagesLiveData.getValue();
        if (messages != null) {
            return messages.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        List<Messages> messages = messagesLiveData.getValue();

        if (messages != null && position < messages.size()) {
            Messages message = messages.get(position);
            SessionManager     sessionManager = new SessionManager(context);
            int currentUserId = sessionManager.getUserId();

            if (message.getSenderId() == currentUserId) {
                return VIEW_TYPE_SENDER;
            } else {
                return VIEW_TYPE_RECEIVER;
            }
        }

        return super.getItemViewType(position);
    }

    public static class SenderViewHolder extends RecyclerView.ViewHolder {
        private TextView messageTextView;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.sender_message_text);
        }
    }

    public static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        private TextView messageTextView;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.receiver_message_text);
        }
    }
}
