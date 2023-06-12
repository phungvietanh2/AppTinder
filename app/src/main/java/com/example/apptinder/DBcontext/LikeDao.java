package com.example.apptinder.DBcontext;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.apptinder.Model.Likes;

import java.util.List;

@Dao
public interface LikeDao {
    @Insert
    void insertlistlike(Likes likes);
    @Query("Select * from Likes")
    List<Likes> listLike();

    @Query("DELETE FROM Likes WHERE postId = :postId AND userId = :userId")
    void deleteLikeByPostAndUser(int postId, int userId);
}
