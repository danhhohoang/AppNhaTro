package com.example.appnhatro.Models;

public class PostStatusModel {
    private String _id;
    private String _postID;
    private int _status;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_postID() {
        return _postID;
    }

    public void set_postID(String _postID) {
        this._postID = _postID;
    }

    public int get_status() {
        return _status;
    }

    public void set_status(int _status) {
        this._status = _status;
    }

    public PostStatusModel(String _id, String _postID, int _status) {
        this._id = _id;
        this._postID = _postID;
        this._status = _status;
    }
}
