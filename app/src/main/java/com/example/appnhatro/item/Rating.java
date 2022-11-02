package com.example.appnhatro.item;

public class Rating {
    private String idPost;
    private String idUser;
    private String rating;

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Rating() {
    }

    public Rating(String idPost, String idUser, String rating) {
        this.idPost = idPost;
        this.idUser = idUser;
        this.rating = rating;
    }
}
