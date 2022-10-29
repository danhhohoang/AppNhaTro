package com.example.appnhatro.Models;

public class UserModel {
    private String _id;
    private String _name;
    private String _email;
    private String _phone;
    private String _password;
    private String _citizenNumber;
    private String _avatar;

    public UserModel(String _id, String _name, String _email, String _phone, String _password, String _citizenNumber, String _avatar) {
        this._id = _id;
        this._name = _name;
        this._email = _email;
        this._phone = _phone;
        this._password = _password;
        this._citizenNumber = _citizenNumber;
        this._avatar = _avatar;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_phone() {
        return _phone;
    }

    public void set_phone(String _phone) {
        this._phone = _phone;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_citizenNumber() {
        return _citizenNumber;
    }

    public void set_citizenNumber(String _citizenNumber) {
        this._citizenNumber = _citizenNumber;
    }

    public String get_avatar() {
        return _avatar;
    }

    public void set_avatar(String _avatar) {
        this._avatar = _avatar;
    }

    public String GetString(String type) {
        String result;
        type = type.toLowerCase();

        if (type.equals("readable")) {
            result = "id: " + get_id() + " ,name: " + get_name() + " ,email: " + get_email() + " ,citizen-number: " + get_citizenNumber() + " ,password: " + get_password() + " ,phone: " + get_phone() + " avatar: " + get_avatar() + "";
        } else {
            result = "" + get_id() + "," + get_name() + "," + get_email() + "," + get_citizenNumber() + "," + get_password() + "," + get_phone() + "," + get_avatar() + "";
        }

        return result;
    }
}
