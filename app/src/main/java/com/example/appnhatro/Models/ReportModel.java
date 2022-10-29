package com.example.appnhatro.Models;

public class ReportModel {

    private String _id;
    private String _userID;
    private String _userTarget;
    private String _title;
    private String _content;
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

    public String get_userTarget() {
        return _userTarget;
    }

    public void set_userTarget(String _userTarget) {
        this._userTarget = _userTarget;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_content() {
        return _content;
    }

    public void set_content(String _content) {
        this._content = _content;
    }

    public String get_postID() {
        return _postID;
    }

    public void set_postID(String _postID) {
        this._postID = _postID;
    }

    public ReportModel(String _id, String _userID, String _userTarget, String _title, String _content, String _postID) {
        this._id = _id;
        this._userID = _userID;
        this._userTarget = _userTarget;
        this._title = _title;
        this._content = _content;
        this._postID = _postID;
    }

}
