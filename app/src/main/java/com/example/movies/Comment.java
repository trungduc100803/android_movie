package com.example.movies;

import java.util.Date;

public class Comment {
    private String user;
    private String content;
    private Date time;
    private String nameMovie;

    public Comment() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getNameMovie() {
        return nameMovie;
    }

    public void setNameMovie(String nameMovie) {
        this.nameMovie = nameMovie;
    }

    public Comment(String user, String content, Date time, String nameMovie) {
        this.user = user;
        this.content = content;
        this.time = time;
        this.nameMovie = nameMovie;
    }
}
