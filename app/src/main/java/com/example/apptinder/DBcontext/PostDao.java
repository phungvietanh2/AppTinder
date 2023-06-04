package com.example.apptinder.DBcontext;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.apptinder.Model.Post;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PostDao {
    @Insert
    void insertpost(Post post);

    @Query("SELECT * FROM Posts")
    List<Post> listPost();

    @Query("SELECT postId FROM Posts ORDER BY postId DESC LIMIT 1")
    int getLatestPostId();

    @Query("SELECT * FROM Posts WHERE postId = :postId")
    Post getPostById(int postId);
}
