package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.ManageEmployees.Classes;

public class EmployeeModel {

    private String id;
    private String name;
    private long phoneNo;
    private long aadharNo;
    private String email;
    private String imgUrl;

    public EmployeeModel() {
    }

    public EmployeeModel(String id, String name, long phoneNo, long aadharNo, String email, String imgUrl) {
        this.id = id;
        this.name = name;
        this.phoneNo = phoneNo;
        this.aadharNo = aadharNo;
        this.email = email;
        this.imgUrl = imgUrl;
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

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public long getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(long aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
