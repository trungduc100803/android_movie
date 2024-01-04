package com.example.movies;

public class Favorite {
    private String userName;
    private CardMovie cardMovie;

    public Favorite(String user, CardMovie cardMovie) {
        this.userName = user;
        this.cardMovie = cardMovie;
    }

    public Favorite() {
    }

    public String getUser() {
        return userName;
    }

    public void setUser(String user) {
        this.userName = user;
    }

    public CardMovie getCardMovie() {
        return cardMovie;
    }

    public void setCardMovie(CardMovie cardMovie) {
        this.cardMovie = cardMovie;
    }
}
