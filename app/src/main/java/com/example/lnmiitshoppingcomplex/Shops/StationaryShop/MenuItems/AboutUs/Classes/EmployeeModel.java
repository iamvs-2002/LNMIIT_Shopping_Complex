package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.MenuItems.AboutUs.Classes;

public class EmployeeModel {
    String name;
    String phonenum;
    String url;

    public EmployeeModel() {
    }

    public EmployeeModel(String name, String phonenum, String url) {
        this.name = name;
        this.phonenum = phonenum;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
