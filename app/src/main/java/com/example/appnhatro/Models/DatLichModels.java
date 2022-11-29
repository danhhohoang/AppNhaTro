package com.example.appnhatro.Models;

public class DatLichModels {
    private String idUser;
    private String name;
    private String Phone;
    private String Time;
    private String Date;
    private String Notes;
    private String id;
    private String idPost;
    private String idLandlord;
    private  String houname;
    private String tt;

    public DatLichModels(String idUser, String name, String phone, String time, String date, String notes, String id, String idPost, String idLandlord, String houname, String tt) {
        this.idUser = idUser;
        this.name = name;
        Phone = phone;
        Time = time;
        Date = date;
        Notes = notes;
        this.id = id;
        this.idPost = idPost;
        this.idLandlord = idLandlord;
        this.houname = houname;
        this.tt = tt;
    }

    public DatLichModels() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getIdLandlord() {
        return idLandlord;
    }

    public void setIdLandlord(String idLandlord) {
        this.idLandlord = idLandlord;
    }

    public String getHouname() {
        return houname;
    }

    public void setHouname(String houname) {
        this.houname = houname;
    }

    public String getTt() {
        return tt;
    }

    public void setTt(String tt) {
        this.tt = tt;
    }
}
