package com.example.appnhatro;

public class PostListFindPeople {
    int post_id,user_id,age_first,age_last,sdt;
    String title,hobby,description,sex;

    public PostListFindPeople() {
    }

    public PostListFindPeople(int post_id, int user_id, int age_first, int age_last, int sdt, String title, String hobby, String description, String sex) {
        this.post_id = post_id;
        this.user_id = user_id;
        this.age_first = age_first;
        this.age_last = age_last;
        this.sdt = sdt;
        this.title = title;
        this.hobby = hobby;
        this.description = description;
        this.sex = sex;
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

    public int getAge_first() {
        return age_first;
    }

    public void setAge_first(int age_first) {
        this.age_first = age_first;
    }

    public int getAge_last() {
        return age_last;
    }

    public void setAge_last(int age_last) {
        this.age_last = age_last;
    }

    public int getSdt() {
        return sdt;
    }

    public void setSdt(int sdt) {
        this.sdt = sdt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
