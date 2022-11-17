package com.example.appnhatro.Models;

public class TransactionModel {

    private String _id;
    private String extraMoney;
    private String id_user;
    private String post;
    private String status;
    private String date;
    private String _commissionFeeID;
    private String total;


    public TransactionModel() {
    }

    public TransactionModel(String _id, String extraMoney, String id_user, String post, String status, String date, String _commissionFeeID, String total) {
        this._id = _id;
        this.extraMoney = extraMoney;
        this.id_user = id_user;
        this.post = post;
        this.status = status;
        this.date = date;
        this._commissionFeeID = _commissionFeeID;
        this.total = total;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getExtraMoney() {
        return extraMoney;
    }

    public void setExtraMoney(String extraMoney) {
        this.extraMoney = extraMoney;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String get_commissionFeeID() {
        return _commissionFeeID;
    }

    public void set_commissionFeeID(String _commissionFeeID) {
        this._commissionFeeID = _commissionFeeID;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
