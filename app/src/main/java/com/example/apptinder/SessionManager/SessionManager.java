package com.example.apptinder.SessionManager;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.apptinder.Model.User;

public class SessionManager {
    private static final String PREF_NAME = "App_Tinder_Session";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_LOGGED_IN = "logged_in";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    private MutableLiveData<User> currentUser = new MutableLiveData<>();

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        setCurrentUser();
    }

    public void loginUser(int userId) {
        editor.putInt(KEY_USER_ID, userId);
        editor.putBoolean(KEY_LOGGED_IN, true);
        editor.apply();
        setCurrentUser();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_LOGGED_IN, false);
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1);
    }

    public void logoutUser() {
        editor.remove(KEY_USER_ID);
        editor.putBoolean(KEY_LOGGED_IN, false);
        editor.apply();
        setCurrentUser();
    }

    private void setCurrentUser() {
        int userId = getUserId();
        if (userId != -1) {
            User user = new User(); // Tạo đối tượng User từ dữ liệu người dùng
            currentUser.setValue(user);
        } else {
            currentUser.setValue(null);
        }
    }
}
