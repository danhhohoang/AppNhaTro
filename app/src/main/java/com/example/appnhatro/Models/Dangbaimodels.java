package com.example.appnhatro.Models;

public class Dangbaimodels {


    private String _status;
    private String _address;
    private String _phoneNumber;
    private String _sex;
    private int _age;
    private int _arrive;
    private String _amount;
    private String _requirements;
    private String _price;
    private String _image1;
    private String _image2;
    private String _image3;


    public Dangbaimodels(String status, String address, String phoneNumber, String sex, int age, int arrive, String amount, String price, String requirements, String image1, String image2, String image3) {
        _status = status;
        _address = address;
        _phoneNumber = phoneNumber;
        _sex = sex;
        _age = age;
        _arrive = arrive;
        _amount = amount;
        _price = price;
        _requirements = requirements;
        _image1 = image1;
        _image2 = image2;
        _image3 = image3;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public String get_phoneNumber() {
        return _phoneNumber;
    }

    public void set_phoneNumber(String _phoneNumber) {
        this._phoneNumber = _phoneNumber;
    }

    public String get_sex() {
        return _sex;
    }

    public void set_sex(String _sex) {
        this._sex = _sex;
    }

    public int get_age() {
        return _age;
    }

    public void set_age(int _age) {
        this._age = _age;
    }

    public int get_arrive() {
        return _arrive;
    }

    public void set_arrive(int _arrive) {
        this._arrive = _arrive;
    }

    public String get_amount() {
        return _amount;
    }

    public void set_amount(String _amount) {
        this._amount = _amount;
    }

    public String get_requirements() {
        return _requirements;
    }

    public void set_requirements(String _requirements) {
        this._requirements = _requirements;
    }

    public String get_price() {
        return _price;
    }

    public void set_price(String _price) {
        this._price = _price;
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
            result = "status: " + get_status() + " ,phone: " + get_phoneNumber() + " ,sex: " + get_sex() + " ,age: " + get_age() + " ,arrive: " + get_arrive() + " ,amount: " + get_amount() + " ,requirements: " + get_requirements() + " ,price: " + get_price() + " image1: " + get_image1() + " image2: " + get_image2() + " image3: " + get_image3() + "";
        } else {
            result = "" + get_status() + "," + get_phoneNumber() + "," + get_sex() + "," + get_age() + "," + get_arrive() + "," + get_amount() + "," + get_requirements() + "," + get_price() + "," + get_image1() + "," + get_image2() + "," + get_image3() + "";
        }

        return result;
    }
}



