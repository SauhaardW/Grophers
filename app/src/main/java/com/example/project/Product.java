package com.example.project;

public class Product {
    private static int NEXT_ID = 1;
    private int id;
    private String name;
    private String brand;
    private double price;
    private String image;

    public Product() {}

    public Product(String name, String brand, double price) {
        this.id = Product.getNextId();
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    public static int getNextId() {
        return Product.NEXT_ID++;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
