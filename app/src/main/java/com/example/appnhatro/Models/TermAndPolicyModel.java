package com.example.appnhatro.Models;

public class TermAndPolicyModel {
    private String _id;
    private String _content;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_content() {
        return _content;
    }

    public void set_content(String _content) {
        this._content = _content;
    }

    public TermAndPolicyModel(String _id, String _content) {
        this._id = _id;
        this._content = _content;
    }

    public String GetString(String type) {
        String result;
        type = type.toLowerCase();

        if (type.equals("readable")) {
            result = "id: " + get_id() + " ,content: " + get_content() + "";
        } else {
            result = "" + get_id() + "," + get_content() + "";
        }

        return result;
    }
}
