package com.example.appnhatro.Models;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Post {
    private String id;
    private String userID;
    private String title;
    private String address;
    private String address_district;
    private String price;
    private String area;
    private String house_name;
    private String image;
    private String status;

    public Post() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_district() {
        return address_district;
    }

    public void setAddress_district(String address_district) {
        this.address_district = address_district;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHouse_name() {
        return house_name;
    }

    public void setHouse_name(String house_name) {
        this.house_name = house_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Post(String id, String userID, String title, String address, String address_district, String price, String area, String house_name, String image, String status) {
        this.id = id;
        this.userID = userID;
        this.title = title;
        this.address = address;
        this.address_district = address_district;
        this.price = price;
        this.area = area;
        this.house_name = house_name;
        this.image = image;
        this.status = status;
    }
}
