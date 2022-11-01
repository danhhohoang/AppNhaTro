package com.example.appnhatro.Models;

public class Favorite {
    private String idUser;
    private String idPost;

    public Favorite() {
    }

    public Favorite(String idUser, String idPost) {
        this.idUser = idUser;
        this.idPost = idPost;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }
}
