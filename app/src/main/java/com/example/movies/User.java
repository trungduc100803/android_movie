package com.example.movies;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private String userName, email, password, passwordRepeat;

    private Boolean status;
    private List<String> videoYeuThich;

    public User(String userName, String email, String password, String passwordRepeat, Boolean status, List<String> videoYeuThich) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.passwordRepeat = passwordRepeat;
        this.status = status;
        this.videoYeuThich = videoYeuThich;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<String> getVideoYeuThich() {
        return videoYeuThich;
    }

    public void setVideoYeuThich(List<String> videoYeuThich) {
        this.videoYeuThich = videoYeuThich;
    }

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }
}
