package com.example.apptinder.DBcontext;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.apptinder.Model.Comments;
import com.example.apptinder.Model.Conversations;
import com.example.apptinder.Model.Friendships;
import com.example.apptinder.Model.Likes;
import com.example.apptinder.Model.Messages;
import com.example.apptinder.Model.Post;
import com.example.apptinder.Model.User;
import com.example.apptinder.Type.Converter;
import com.example.apptinder.Type.DateTypeConverter;

@Database(entities = {User.class, Post.class, Comments.class, Likes.class, Messages.class, Conversations.class, Friendships.class}, version = 1, exportSchema = false)
@TypeConverters({DateTypeConverter.class, Converter.class})
public abstract class DBcontext extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract PostDao postDao();

    public abstract FriendshipDao friendshipDao();

    public abstract CommentDao commentDao();

    public abstract LikeDao likeDao();

    public abstract MessageDao messageDao();

    public abstract ConversationDao conversationDao();


    private static DBcontext INSTANCE;

    public static DBcontext getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            DBcontext.class, "App_Tinder").allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;

    }


}
