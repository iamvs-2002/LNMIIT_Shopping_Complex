package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.ManageEmployees.Classes;

public class EmployeeModel {

    private int id;
    private String name;
    private String phoneNumber;
    private String aadharNumber;
    private String email;

    public EmployeeModel(String name, String phoneNumber, String aadharNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.aadharNumber = aadharNumber;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
