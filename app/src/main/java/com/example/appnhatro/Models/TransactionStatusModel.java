package com.example.appnhatro.Models;

public class TransactionStatusModel {
    private String _id;
    private String _transactionID;
    private int _status;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_transactionID() {
        return _transactionID;
    }

    public void set_transactionID(String _transactionID) {
        this._transactionID = _transactionID;
    }

    public int get_status() {
        return _status;
    }

    public void set_status(int _status) {
        this._status = _status;
    }

    public TransactionStatusModel(String _id, String _transactionID, int _status) {
        this._id = _id;
        this._transactionID = _transactionID;
        this._status = _status;
    }
}
