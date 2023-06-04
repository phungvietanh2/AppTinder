package com.example.apptinder.DBcontext;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.apptinder.Model.User;

@Dao
public interface UserDao {
    @Insert
    void insertuser(User... users);

    @Query("SELECT * FROM users WHERE  email= :email AND password = :password")
     User login(String email , String password);

    @Query("SELECT * FROM users WHERE userId = :id")
    User getUserById(int id);
}
