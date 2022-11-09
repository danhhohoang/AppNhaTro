package com.example.appnhatro.Models;

public class PostFindRoomateModel {
    private String _id;
    private String _postID;
    private String _userTenantID;
    private String _title;
    private int _ageMin;
    private int _ageMax;
    private String _hobby;
    private String _userTenantPhone ;

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

    public String get_userTenantID() {
        return _userTenantID;
    }

    public void set_userTenantID(String _userTenantID) {
        this._userTenantID = _userTenantID;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public int get_ageMin() {
        return _ageMin;
    }

    public void set_ageMin(int _ageMin) {
        this._ageMin = _ageMin;
    }

    public int get_ageMax() {
        return _ageMax;
    }

    public void set_ageMax(int _ageMax) {
        this._ageMax = _ageMax;
    }

    public String get_hobby() {
        return _hobby;
    }

    public void set_hobby(String _hobby) {
        this._hobby = _hobby;
    }

    public String get_userTenantPhone() {
        return _userTenantPhone;
    }

    public void set_userTenantPhone(String _userTenantPhone) {
        this._userTenantPhone = _userTenantPhone;
    }

    public PostFindRoomateModel(String _id, String _postID, String _userTenantID, String _title, int _ageMin, int _ageMax, String _hobby, String _userTenantPhone) {
        this._id = _id;
        this._postID = _postID;
        this._userTenantID = _userTenantID;
        this._title = _title;
        this._ageMin = _ageMin;
        this._ageMax = _ageMax;
        this._hobby = _hobby;
        this._userTenantPhone = _userTenantPhone;
    }

    public PostFindRoomateModel() {
    }
}
