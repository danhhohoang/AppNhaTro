package com.example.appnhatro.Models;

public class NotificationModel {
    private String _id;
    private String _userID;
    private String _content;

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

    public String get_content() {
        return _content;
    }

    public void set_content(String _content) {
        this._content = _content;
    }

    public NotificationModel(String _id, String _userID, String _content) {
        this._id = _id;
        this._userID = _userID;
        this._content = _content;
    }

    public String GetString(String type) {
        String result;
        type = type.toLowerCase();

        if (type.equals("readable")) {
            result = "id: " + get_id() + " ,user-id: " + get_userID() + " ,content: " + get_content() + "";
        } else {
            result = "" + get_id() + "," + get_userID() +"," + get_content() + "";
        }

        return result;
    }
}
