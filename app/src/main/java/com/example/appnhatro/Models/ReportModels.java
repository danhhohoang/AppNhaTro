package com.example.appnhatro.Models;

public class ReportModels {
    private String id;
    private String content;
    private String id_report;
    private String id_user;
    private String post_id;
    private String title;

    public ReportModels(String id, String content, String id_report, String id_user, String post_id, String title) {
        this.id = id;
        this.content = content;
        this.id_report = id_report;
        this.id_user = id_user;
        this.post_id = post_id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId_report() {
        return id_report;
    }

    public void setId_report(String id_report) {
        this.id_report = id_report;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
