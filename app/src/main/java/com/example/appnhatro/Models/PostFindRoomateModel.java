package com.example.appnhatro.Models;

public class PostFindRoomateModel {
    private String id_user;
    private String postID;
    private String userTenantID;
    private String title;
    private String age_first;
    private String age_last;
    private String hobby;
    private String sdt ;
    private String description;

    public PostFindRoomateModel() {
    }

    public PostFindRoomateModel(String id_user, String postID, String userTenantID, String title, String age_first, String age_last, String hobby, String sdt, String description) {
        this.id_user = id_user;
        this.postID = postID;
        this.userTenantID = userTenantID;
        this.title = title;
        this.age_first = age_first;
        this.age_last = age_last;
        this.hobby = hobby;
        this.sdt = sdt;
        this.description = description;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
