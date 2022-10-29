package com.example.appnhatro.Models;

public class LikedPostModel {

    private String _id;
    private String _userID;
    private String _postID;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_userID() {
        return _userID;
    }

    public void set_userID(String _userID) {
        this._userID = _userID;
    }

    public String get_postID() {
        return _postID;
    }

    public void set_postID(String _postID) {
        this._postID = _postID;
    }

    public LikedPostModel(String _id, String _userID, String _postID) {
        this._id = _id;
        this._userID = _userID;
        this._postID = _postID;
    }
}
