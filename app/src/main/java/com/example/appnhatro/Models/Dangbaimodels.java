package com.example.appnhatro.Models;

public class Dangbaimodels {
    private String id;
    private String idUser;
    private String Status;
    private String address;
    private String phone;
    private String Gender;
    private String age;
    private String arrive;
    private String amount;
    private String OtherRequirements;
    private String price;
    private String image1;


    public Dangbaimodels() {
    }

    public Dangbaimodels(String id, String idUser, String status, String address, String phone, String gender, String age, String arrive, String amount, String otherRequirements, String price, String image1) {
        this.id = id;
        this.idUser = idUser;
        Status = status;
        this.address = address;
        this.phone = phone;
        Gender = gender;
        this.age = age;
        this.arrive = arrive;
        this.amount = amount;
        OtherRequirements = otherRequirements;
        this.price = price;
        this.image1 = image1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOtherRequirements() {
        return OtherRequirements;
    }

    public void setOtherRequirements(String otherRequirements) {
        OtherRequirements = otherRequirements;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }
}



