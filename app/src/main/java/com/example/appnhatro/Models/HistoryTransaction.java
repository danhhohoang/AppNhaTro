package com.example.appnhatro.Models;

public class HistoryTransaction {
    private String id;
    private String price;
    private String id_user;
    private String id_landlord;
    private String post;
    private String status;
    private String date;
    private String deposits;

    public String getId_landlord() {
        return id_landlord;
    }

    public void setId_landlord(String id_landlord) {
        this.id_landlord = id_landlord;
    }

    public String getDeposits() {
        return deposits;
    }

    public void setDeposits(String deposits) {
        this.deposits = deposits;
    }

    public HistoryTransaction(String id, String price, String id_user, String id_landlord, String post, String status, String date, String deposits) {
        this.id = id;
        this.price = price;
        this.id_user = id_user;
        this.id_landlord = id_landlord;
        this.post = post;
        this.status = status;
        this.date = date;
        this.deposits = deposits;
    }

    public HistoryTransaction() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
