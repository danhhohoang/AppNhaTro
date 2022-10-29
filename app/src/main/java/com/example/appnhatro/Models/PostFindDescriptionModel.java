package com.example.appnhatro.Models;

public class PostFindDescriptionModel {
    private String _id;
    private String _postFindRoomateID;
    private String _content;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_postFindRoomateID() {
        return _postFindRoomateID;
    }

    public void set_postFindRoomateID(String _postFindRoomateID) {
        this._postFindRoomateID = _postFindRoomateID;
    }

    public String get_content() {
        return _content;
    }

    public void set_content(String _content) {
        this._content = _content;
    }

    public PostFindDescriptionModel(String _id, String _postFindRoomateID, String _content) {
        this._id = _id;
        this._postFindRoomateID = _postFindRoomateID;
        this._content = _content;
    }
}
