package com.example.appnhatro.Models;

public class PostModel {


    private String _id;
    private String _userID;
    private String _title;
    private int _price;
    private int _limitMonth;
    private int _area;
    private String _image1;
    private String _image2;
    private String _image3;


    public PostModel(String id, String userId, String title, int price, int limitMonth, int area, String image1, String image2, String image3) {
        _id = id;
        _userID = userId;
        _title = title;
        _price = price;
        _limitMonth = limitMonth;
        _area = area;
        _image1 = image1;
        _image2 = image2;
        _image3 = image3;
    }

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

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public int get_price() {
        return _price;
    }

    public void set_price(int _price) {
        this._price = _price;
    }

    public int get_limitMonth() {
        return _limitMonth;
    }

    public void set_limitMonth(int _limitMonth) {
        this._limitMonth = _limitMonth;
    }

    public int get_area() {
        return _area;
    }

    public void set_area(int _area) {
        this._area = _area;
    }

    public String get_image1() {
        return _image1;
    }

    public void set_image1(String _image1) {
        this._image1 = _image1;
    }

    public String get_image2() {
        return _image2;
    }

    public void set_image2(String _image2) {
        this._image2 = _image2;
    }

    public String get_image3() {
        return _image3;
    }

    public void set_image3(String _image3) {
        this._image3 = _image3;
    }

    public String GetString(String type) {
        String result;
        type = type.toLowerCase();

        if (type.equals("readable")) {
            result = "id: " + get_id() + " ,userID: " + get_userID() + " ,title: " + get_title() + " ,price: " + get_price() + " ,limit-month: " + get_limitMonth() + " ,area: " + get_area() + " image1: " + get_image1() + " image2: " + get_image2() + " image3: " + get_image3() + "";
        } else {
            result = "" + get_id() + "," + get_userID() + "," + get_title() + "," + get_price() + "," + get_limitMonth() + "," + get_area() + "," + get_image1() + "," + get_image2() + "," + get_image3() + "";
        }

        return result;
    }
}
