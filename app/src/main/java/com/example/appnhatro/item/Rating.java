package com.example.appnhatro.item;

import java.io.Serializable;

public class Rating implements Serializable {
    private String idPost;
    private String idUser;
    private String rating;
    private String comment;
    private String feedback;
    private String date;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Rating() {
    }

    public Rating(String idPost, String idUser, String rating, String comment, String feedback, String date) {
        this.idPost = idPost;
        this.idUser = idUser;
        this.rating = rating;
        this.comment = comment;
        this.feedback = feedback;
        this.date = date;
    }
}
