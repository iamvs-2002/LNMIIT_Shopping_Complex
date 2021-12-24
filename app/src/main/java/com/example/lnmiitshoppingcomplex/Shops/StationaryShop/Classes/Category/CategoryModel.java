package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.Classes.Category;

public class CategoryModel {
    String name;
    int url;

    public CategoryModel() {
    }

    public CategoryModel(String name, int url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }
}
