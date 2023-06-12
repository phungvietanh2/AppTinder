package com.example.apptinder.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "Likes",
        foreignKeys = {@ForeignKey(entity = Post.class,
                parentColumns = "postId",
                childColumns = "postId",
                onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = User.class,
                        parentColumns = "userId",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE)})
public class Likes {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "likeId")
    public int likeId;
    @ColumnInfo(name = "postId")
    public int postId;
    @ColumnInfo(name = "userId")
    public int userId;
    @ColumnInfo(name = "timestamp")
    public String timestamp;

    public Likes() {
    }

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Likes{" +
                "likeId=" + likeId +
                ", postId=" + postId +
                ", userId=" + userId +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
