package com.example.appnhatro.Models;

import android.graphics.Bitmap;

public class BitMap {
    private String tenHinh;
    private Bitmap hinh;

    public BitMap(String tenHinh, Bitmap hinh) {
        this.tenHinh = tenHinh;
        this.hinh = hinh;
    }

    public BitMap() {
    }

    public String getTenHinh() {
        return tenHinh;
    }

    public void setTenHinh(String tenHinh) {
        this.tenHinh = tenHinh;
    }

    public Bitmap getHinh() {
        return hinh;
    }

    public void setHinh(Bitmap hinh) {
        this.hinh = hinh;
    }
}
