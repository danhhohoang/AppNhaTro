package com.example.appnhatro;

public class LandLordList {
    int ID,sdt,moneyhavepay;
    String status;

    public LandLordList() {
    }

    public LandLordList(int ID, int sdt, int moneyhavepay, String status) {
        this.ID = ID;
        this.sdt = sdt;
        this.moneyhavepay = moneyhavepay;
        this.status = status;
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
}
