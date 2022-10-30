package com.example.appnhatro.FakeModels;

public class PostFake {
    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostAddress() {
        return postAddress;
    }

    public void setPostAddress(String postAddress) {
        this.postAddress = postAddress;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public String getPostAvatarUrl() {
        return postAvatarUrl;
    }

    public void setPostAvatarUrl(String postAvatarUrl) {
        this.postAvatarUrl = postAvatarUrl;
    }

    public int getPostArea() {
        return postArea;
    }

    public void setPostArea(int postArea) {
        this.postArea = postArea;
    }

    public int getPostPrice() {
        return postPrice;
    }

    public void setPostPrice(int postPrice) {
        this.postPrice = postPrice;
    }

    public String getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus;
    }

    public PostFake(String postTitle, String postAddress, String postImageUrl, String postAvatarUrl, int postArea, int postPrice, String postStatus) {
        this.postTitle = postTitle;
        this.postAddress = postAddress;
        this.postImageUrl = postImageUrl;
        this.postAvatarUrl = postAvatarUrl;
        this.postArea = postArea;
        this.postPrice = postPrice;
        this.postStatus = postStatus;
    }

    private String postTitle;
    private String postAddress;
    private String postImageUrl;
    private String postAvatarUrl;
    private int postArea;
    private int postPrice;
    private String postStatus;


}
