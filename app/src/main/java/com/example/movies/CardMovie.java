package com.example.movies;

import java.io.Serializable;

public class CardMovie implements Serializable {

    private String resourceImage;
    private  String title;
    private String descriptions;
    private String author;
    private  String time;

    private String urlVideo;

    public CardMovie(String resourceImage, String title, String descriptions, String author, String time, String  urlVideo) {
        this.resourceImage = resourceImage;
        this.title = title;
        this.descriptions = descriptions;
        this.author = author;
        this.time = time;
        this.urlVideo = urlVideo;
    }

    public CardMovie() {
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResourceImage() {
        return resourceImage;
    }

    public void setResourceImage(String resourceImage) {
        this.resourceImage = resourceImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "CardMovie{" +
                "resourceImage='" + resourceImage + '\'' +
                ", title='" + title + '\'' +
                ", descriptions='" + descriptions + '\'' +
                ", author='" + author + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
