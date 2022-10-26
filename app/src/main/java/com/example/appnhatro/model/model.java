package com.example.appnhatro.model;

public class model {

    String tenantAvatar;
    String tenantID;
    String tenantPhoneNumber;

    public String getTenantAvatar() {
        return tenantAvatar;
    }

    public void setTenantAvatar(String tenantAvatar) {
        this.tenantAvatar = tenantAvatar;
    }

    public String getTenantID() {
        return tenantID;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public String getTenantPhoneNumber() {
        return tenantPhoneNumber;
    }

    public void setTenantPhoneNumber(String tenantPhoneNumber) {
        this.tenantPhoneNumber = tenantPhoneNumber;
    }
    public model(String tenantAvatar, String tenantID, String tenantPhoneNumber) {
        this.tenantAvatar = tenantAvatar;
        this.tenantID = tenantID;
        this.tenantPhoneNumber = tenantPhoneNumber;
    }
}
