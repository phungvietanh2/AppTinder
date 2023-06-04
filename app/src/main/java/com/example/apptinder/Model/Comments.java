package com.example.apptinder.Model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "Comments",
        foreignKeys = {@ForeignKey(entity = Post.class,
                parentColumns = "postId",
                childColumns = "postId",
                onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = User.class,
                        parentColumns = "userId",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE)})
public class Comments {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "commentId")
    public int commentId;
    @ColumnInfo(name = "postId")
    public int postId;
    @ColumnInfo(name = "userId")
    public int userId;
    @ColumnInfo(name = "content")
    public String content;
    @ColumnInfo(name = "timestamp")
    public String timestamp;

    public Comments() {
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
