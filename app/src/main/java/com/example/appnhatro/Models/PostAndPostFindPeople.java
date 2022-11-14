package com.example.appnhatro.Models;

public class PostAndPostFindPeople {
    private String id_user;
    private String postID;
    private String userTenantID;
    private String titlePFPP;
    private String age_first;
    private String age_last;
    private String hobby;
    private String sdt ;
    private String description;

    private String id;
    private String userID;
    private String title;
    private String address;
    private String address_district;
    private String price;
    private String limitMonth;
    private String area;
    private String house_name;
    private String image;

    public PostAndPostFindPeople() {
    }

    public PostAndPostFindPeople(String id_user, String postID, String userTenantID, String titlePFPP, String age_first, String age_last, String hobby, String sdt, String description, String id, String userID, String title, String address, String address_district, String price, String limitMonth, String area, String house_name, String image) {
        this.id_user = id_user;
        this.postID = postID;
        this.userTenantID = userTenantID;
        this.titlePFPP = titlePFPP;
        this.age_first = age_first;
        this.age_last = age_last;
        this.hobby = hobby;
        this.sdt = sdt;
        this.description = description;
        this.id = id;
        this.userID = userID;
        this.title = title;
        this.address = address;
        this.address_district = address_district;
        this.price = price;
        this.limitMonth = limitMonth;
        this.area = area;
        this.house_name = house_name;
        this.image = image;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getUserTenantID() {
        return userTenantID;
    }

    public void setUserTenantID(String userTenantID) {
        this.userTenantID = userTenantID;
    }

    public String getTitlePFPP() {
        return titlePFPP;
    }

    public void setTitlePFPP(String titlePFPP) {
        this.titlePFPP = titlePFPP;
    }

    public String getAge_first() {
        return age_first;
    }

    public void setAge_first(String age_first) {
        this.age_first = age_first;
    }

    public String getAge_last() {
        return age_last;
    }

    public void setAge_last(String age_last) {
        this.age_last = age_last;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getLimitMonth() {
        return limitMonth;
    }

    public void setLimitMonth(String limitMonth) {
        this.limitMonth = limitMonth;
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
}
