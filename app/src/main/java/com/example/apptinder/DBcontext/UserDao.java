package com.example.apptinder.DBcontext;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.apptinder.Model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertuser(User... users);

    @Query("SELECT * FROM users WHERE  email= :email AND password = :password")
    User login(String email, String password);

    @Query("SELECT * FROM users WHERE userId = :id")
    User getUserById(int id);

    @Query("UPDATE users SET username = :username , avatar= :avatar, age= :age, gender = :gender ,text= :text WHERE userId = :userId")
    void updateUser(int userId, String username, String avatar, int age, Boolean gender, String text);

    @Query("SELECT * FROM users")
    List<User> getUserList();

    @Query("SELECT * FROM users WHERE userId = :id")
    List<User> getList(int id);

    @Query("UPDATE users SET avatar= :avatar, age= :age, gender = :gender ,text= :text WHERE userId = :userId")
    void updateUser1(int userId, String avatar, int age, Boolean gender, String text);


}
