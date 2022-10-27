package com.example.appnhatro;

public class PostList {
    int post_id,user_id,area,price;
    String house_name,address,attachment,status,wishlist;

    public PostList() {
    }

    public PostList(int post_id, int user_id, int area, int price, String house_name, String address, String attachment, String status, String wishlist) {
        this.post_id = post_id;
        this.user_id = user_id;
        this.area = area;
        this.price = price;
        this.house_name = house_name;
        this.address = address;
        this.attachment = attachment;
        this.status = status;
        this.wishlist = wishlist;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getHouse_name() {
        return house_name;
    }

    public void setHouse_name(String house_name) {
        this.house_name = house_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWishlist() {
        return wishlist;
    }

    public void setWishlist(String wishlist) {
        this.wishlist = wishlist;
    }
}
