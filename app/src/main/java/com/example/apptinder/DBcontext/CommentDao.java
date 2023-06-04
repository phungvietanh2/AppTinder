package com.example.apptinder.DBcontext;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.apptinder.Model.Comments;

import java.util.List;

@Dao
public interface CommentDao {
    @Query("SELECT * FROM Comments")
    List<Comments> getAllComments();

    @Insert
    void insertcomment(Comments comments);

    @Query("SELECT COUNT(*) FROM Comments WHERE postId = :postId")
    int getCommentCountByPostId(int postId);

}
