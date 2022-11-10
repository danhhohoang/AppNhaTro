package com.example.appnhatro.Activity;

public class landlord_report_models {
    String Name, MaReport, NoiDung, MaPhong;
    int imageID;

    public landlord_report_models(String name, String maReport, String noiDung, String maPhong, int imageID) {
        Name = name;
        MaReport = maReport;
        NoiDung = noiDung;
        MaPhong = maPhong;
        this.imageID = imageID;
    }

    public landlord_report_models() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMaReport() {
        return MaReport;
    }

    public void setMaReport(String maReport) {
        MaReport = maReport;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public String getMaPhong() {
        return MaPhong;
    }

    public void setMaPhong(String maPhong) {
        MaPhong = maPhong;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}

