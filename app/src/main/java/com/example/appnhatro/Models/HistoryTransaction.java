package com.example.appnhatro.Models;

public class HistoryTransaction {
    private String id;
    private String id_user;
    private String id_landlord;
    private String post;
    private String status;
    private String total;
    private String date;

    public String getId_landlord() {
        return id_landlord;
    }

    public void setId_landlord(String id_landlord) {
        this.id_landlord = id_landlord;
    }

    public HistoryTransaction(String id, String id_user, String id_landlord, String post, String status, String total, String date) {
        this.id = id;
        this.id_user = id_user;
        this.id_landlord = id_landlord;
        this.post = post;
        this.status = status;
        this.total = total;
        this.date = date;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
