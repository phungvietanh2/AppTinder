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
public interface ConversationDao {
    @Insert
    void insertConversation(Conversations conversation);

    @Query("SELECT * FROM Conversations WHERE (userId1 = :userId1 AND userId2 = :userId2) OR (userId1 = :userId2 AND userId2 = :userId1)")
    LiveData<List<Conversations>>  getConversationByUsers(int userId1, int userId2);


    // kiểm tra xem cuộc trò chuyện giữa hai người dùng đã tồn tại hay chưa
    @Query("SELECT * FROM Conversations WHERE conversationId = :conversationId")
    LiveData<Conversations> getConversationById(int conversationId);

    @Query("SELECT * FROM Conversations WHERE userId1 = :userId OR userId2 = :userId")
    LiveData<List<Conversations>> getConversationsByUser(int userId);
    @Query("SELECT * FROM Conversations")
    LiveData<List<Conversations>> getAllConversations();

    @Query("SELECT c.*, m.content, m.timestamp " +
            "FROM Conversations c " +
            "INNER JOIN Messages m ON c.lastMessageId = m.messageId " +
            "WHERE c.userId1 = :userId OR c.userId2 = :userId " +
            "ORDER BY m.timestamp DESC")
    LiveData<List<Conversations>> getConversationsWithLatestMessage(int userId);

    @Query("SELECT DISTINCT c.*, m.content, m.timestamp " +
            "FROM Conversations c " +
            "INNER JOIN Messages m ON c.lastMessageId = m.messageId " +
            "WHERE c.userId1 = :userId OR c.userId2 = :userId " +
            "ORDER BY m.timestamp DESC")
    LiveData<List<Conversations>> getConversationsWithLatestMessage1(int userId);

    @Query("SELECT c.*, m.content, m.timestamp " +
            "FROM Conversations c " +
            "INNER JOIN Messages m ON c.lastMessageId = m.messageId " +
            "WHERE (c.userId1 = :userId OR c.userId2 = :userId) " +
            "ORDER BY m.timestamp DESC " +
            "LIMIT 1")
    LiveData<List<Conversations>> getLatestConversationWithMessage(int userId);



    @Query("SELECT c.conversationId, c.userId1,c.userId2, MAX(m.messageId) AS latestMessageId, m.content, m.timestamp " +
            "FROM Conversations c " +
            "INNER JOIN Messages m ON c.lastMessageId = m.messageId " +
            "WHERE c.userId1 = :userId OR c.userId2 = :userId " +
            "ORDER BY c.conversationId DESC")
    LiveData<List<Conversations>> getConversationsWithLatestMessage123(int userId);

    @Query("UPDATE Conversations SET lastMessageId = :messageId WHERE (userId1 = :userId1 AND userId2 = :userId2) OR (userId1 = :userId2 AND userId2 = :userId1)")
    void updateLastMessageId(int userId1, int userId2, long messageId);

    @Delete
    void deleteConversation(Conversations conversation);

    // Hoặc sử dụng câu lệnh SQL để xóa cuộc trò chuyện bằng conversationId
    @Query("DELETE FROM Conversations WHERE conversationId = :conversationId")
    void deleteConversationById(int conversationId);

}
