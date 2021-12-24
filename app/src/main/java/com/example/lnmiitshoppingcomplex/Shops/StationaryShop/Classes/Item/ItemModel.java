package com.example.lnmiitshoppingcomplex.Shops.StationaryShop.Classes.Item;

public class ItemModel {
    String name;
    int price;
    String imgurl;
    int quantity;

    public ItemModel() {
    }

    public ItemModel(String name, int price, String imgurl, int quantity) {
        this.name = name;
        this.price = price;
        this.imgurl = imgurl;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
