package com.example.appnhatro.Models;

public class ImagePost {
    public ImagePost() {
    }

    private String tenHinh;
    private String maHinh;
    private String idHinh;

    public ImagePost(String tenHinh, String maHinh, String idHinh) {
        this.tenHinh = tenHinh;
        this.maHinh = maHinh;
        this.idHinh = idHinh;
    }

    public String getTenHinh() {
        return tenHinh;
    }

    public void setTenHinh(String tenHinh) {
        this.tenHinh = tenHinh;
    }

    public String getMaHinh() {
        return maHinh;
    }

    public void setMaHinh(String maHinh) {
        this.maHinh = maHinh;
    }

    public String getIdHinh() {
        return idHinh;
    }

    public void setIdHinh(String idHinh) {
        this.idHinh = idHinh;
    }
}
