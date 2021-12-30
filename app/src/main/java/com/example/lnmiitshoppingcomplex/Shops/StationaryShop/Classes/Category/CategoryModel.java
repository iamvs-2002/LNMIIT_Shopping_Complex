package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.Classes.Category;

public class CategoryModel {

    String id;
    String name;
    String imgUrl;

    public CategoryModel() {
    }


    public CategoryModel(String id, String name, String imgUrl) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public CategoryModel(String name) {
        this.name = name;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
