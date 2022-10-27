package com.example.appnhatro;

public class Room {
    private String maPhong;

    public Room(String maPhong, String tenPhong, String hinhAnh, int gia) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.hinhAnh = hinhAnh;
        this.gia = gia;
    }

    private String tenPhong;
    private String hinhAnh;
    private int gia;

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public Room(String tenPhong, String hinhAnh, int gia) {
        this.tenPhong = tenPhong;
        this.hinhAnh = hinhAnh;
        this.gia = gia;
    }
}
