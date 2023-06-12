package com.example.apptinder.Model;

import static androidx.room.ForeignKey.CASCADE;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.util.List;

@Entity(tableName = "Posts",
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "userId",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE))
public class Post implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "postId")
    public int postId;
    @ColumnInfo(name = "userId")
    public int userId;
    @ColumnInfo(name = "content")
    public String content;
    @ColumnInfo(name = "timestamp")
    public String timestamp;
    @ColumnInfo(name = "image")
    public List<String> images;
    @ColumnInfo(name = "isLiked")
    private boolean isLiked;

    public Post() {
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    @Override
    public String toString() {
        return "Post{" +
                "images=" + images +
                '}';
    }

    protected Post(Parcel in) {
        postId = in.readInt();
        userId = in.readInt();
        content = in.readString();
        timestamp = in.readString();
        images = in.createStringArrayList();
        isLiked = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(postId);
        dest.writeInt(userId);
        dest.writeString(content);
        dest.writeString(timestamp);
        dest.writeStringList(images);
        dest.writeByte((byte) (isLiked ? 1 : 0));
    }

}

