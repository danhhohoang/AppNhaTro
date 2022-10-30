package com.example.appnhatro;

public class LandLordList {
    int ID,sdt,moneyhavepay;
    String status,tenchutro,tentaikhoan,cmnd,diachi;

    public LandLordList() {
    }

    public LandLordList(int ID, int sdt, int moneyhavepay, String status, String tenchutro, String tentaikhoan, String cmnd, String diachi) {
        this.ID = ID;
        this.sdt = sdt;
        this.moneyhavepay = moneyhavepay;
        this.status = status;
        this.tenchutro = tenchutro;
        this.tentaikhoan = tentaikhoan;
        this.cmnd = cmnd;
        this.diachi = diachi;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSdt() {
        return sdt;
    }

    public void setSdt(int sdt) {
        this.sdt = sdt;
    }

    public int getMoneyhavepay() {
        return moneyhavepay;
    }

    public void setMoneyhavepay(int moneyhavepay) {
        this.moneyhavepay = moneyhavepay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTenchutro() {
        return tenchutro;
    }

    public void setTenchutro(String tenchutro) {
        this.tenchutro = tenchutro;
    }

    public String getTentaikhoan() {
        return tentaikhoan;
    }

    public void setTentaikhoan(String tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
}
