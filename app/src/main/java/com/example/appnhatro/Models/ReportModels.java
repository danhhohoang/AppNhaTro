package com.example.appnhatro.Models;

public class ReportModels {
    private String id;
    private String Post_Name;
    private String ID_NguoiDang;
    private String ID_BaiDang;
    private String TenNguoiGui;
    private String title;
    private String content;
    private String status;

    public ReportModels(String id, String post_Name, String ID_NguoiDang, String ID_BaiDang, String tenNguoiGui, String title, String content, String status) {
        this.id = id;
        Post_Name = post_Name;
        this.ID_NguoiDang = ID_NguoiDang;
        this.ID_BaiDang = ID_BaiDang;
        TenNguoiGui = tenNguoiGui;
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public ReportModels() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPost_Name() {
        return Post_Name;
    }

    public void setPost_Name(String post_Name) {
        Post_Name = post_Name;
    }

    public String getID_NguoiDang() {
        return ID_NguoiDang;
    }

    public void setID_NguoiDang(String ID_NguoiDang) {
        this.ID_NguoiDang = ID_NguoiDang;
    }

    public String getID_BaiDang() {
        return ID_BaiDang;
    }

    public void setID_BaiDang(String ID_BaiDang) {
        this.ID_BaiDang = ID_BaiDang;
    }

    public String getTenNguoiGui() {
        return TenNguoiGui;
    }

    public void setTenNguoiGui(String tenNguoiGui) {
        TenNguoiGui = tenNguoiGui;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
