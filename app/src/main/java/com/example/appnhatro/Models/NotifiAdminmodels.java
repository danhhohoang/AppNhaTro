package com.example.appnhatro.Models;

public class NotifiAdminmodels {
    private String iduser;
    private String name;
    private String note;
    private String idnotifi;
    private String status;
    private String house_name;

    public NotifiAdminmodels(String iduser, String name, String note, String idnotifi, String status, String house_name) {
        this.iduser = iduser;
        this.name = name;
        this.note = note;
        this.idnotifi = idnotifi;
        this.status = status;
        this.house_name = house_name;
    }

    public NotifiAdminmodels(String iduser) {
        this.iduser = iduser;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getIdnotifi() {
        return idnotifi;
    }

    public void setIdnotifi(String idnotifi) {
        this.idnotifi = idnotifi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHouse_name() {
        return house_name;
    }

    public void setHouse_name(String house_name) {
        this.house_name = house_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
