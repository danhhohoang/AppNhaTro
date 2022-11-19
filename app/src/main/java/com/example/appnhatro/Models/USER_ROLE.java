package com.example.appnhatro.Models;

public class USER_ROLE {
    private String id_role;
    private String id_user;

    public USER_ROLE() {
    }

    public USER_ROLE(String id_role, String id_user) {
        this.id_role = id_role;
        this.id_user = id_user;
    }

    public String getId_role() {
        return id_role;
    }

    public void setId_role(String id_role) {
        this.id_role = id_role;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
