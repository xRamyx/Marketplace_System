package com.example.shoppingcart;

import javafx.scene.image.ImageView;

public class Product {
    private String id;
    private String name;
    private double price;
    private String category;
    private long qty;
    private String image;
    private ImageView imgView;

    public Product(){

    }
    public Product(String name, float price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Product(String id, String name, double price, String category, long qty, String image, ImageView imgView) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.qty = qty;
        this.image = image;
        this.imgView = imgView;
    }

    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public String getImage() {
        return image;
    }
    public String getCategory() { return category; }
    public long getQty() { return qty; }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public void setImgView(ImageView imgView) {
        this.imgView = imgView;
    }
}
