package com.example.movies;

import android.app.Application;

public class Global extends Application {
    private int userID;
    private String userName;
    private static Global instance;

    public Global() {
    }
    public static Global getInstance() {
        if (instance == null) {
            instance = new Global();
        }
        return instance;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
