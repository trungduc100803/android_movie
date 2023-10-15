package com.example.movies;

public class Favorite {
    private User user;
    private CardMovie cardMovie;

    public Favorite(User user, CardMovie cardMovie) {
        this.user = user;
        this.cardMovie = cardMovie;
    }

    public Favorite() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CardMovie getCardMovie() {
        return cardMovie;
    }

    public void setCardMovie(CardMovie cardMovie) {
        this.cardMovie = cardMovie;
    }
}
