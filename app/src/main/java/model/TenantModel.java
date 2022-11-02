package model;

public class TenantModel {
    private String avatar;
    private String cmnd;
    private String email;
    private String id;
    private String name;
    private String password;
    private String sdt;

    public TenantModel(String avatar, String cmnd, String email, String id, String name, String password, String sdtl) {
        this.avatar = avatar;
        this.cmnd = cmnd;
        this.email = email;
        this.id = id;
        this.name = name;
        this.password = password;
        this.sdt = sdtl;
    }

    public TenantModel() {
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdtl(String sdtl) {
        this.sdt = sdtl;
    }
}
