package com.example.apptinder.DBcontext;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.apptinder.Model.Conversations;
import com.example.apptinder.Model.Messages;

import java.util.List;

@Dao
public interface MessageDao {
    @Insert
    long insertMessage(Messages message);

    //danh sách tin nhắn chi tiet cua 2 nguoi

    @Query("SELECT * FROM Messages WHERE (senderId = :senderId AND receiverId = :receiverId) OR (senderId = :receiverId AND receiverId = :senderId) ORDER BY timestamp ASC")
    LiveData<List<Messages>> getMessagesByUsers(int senderId, int receiverId);

    //danh sách tin nhắn chi tiet cua nguoi dung
    @Query("SELECT * FROM Messages WHERE receiverId = :conversationId ORDER BY messageId ASC")
    LiveData<List<Messages>> getMessagesByConversation(int conversationId);
    @Query("SELECT DISTINCT receiverId FROM Messages WHERE senderId = :senderId")
    LiveData<List<Integer>> getReceiverIdsBySenderId(int senderId);

    @Query("SELECT * FROM Messages")
    LiveData<List<Messages>> getAllMessages();

    @Query("DELETE FROM Messages WHERE messageId = :messageId")
    void deleteMessageAndUpdateConversation(int messageId);

    @Delete
    void deletemesss(Messages messages);

    @Query("DELETE FROM Messages WHERE senderId = :senderId")
    void deleteMessagesender(int senderId);

}