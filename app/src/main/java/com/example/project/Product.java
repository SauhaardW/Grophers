package com.example.project;

public class Product {
    private static int NEXT_ID = 0;
    int id;
    String name;
    String brand;
    double price;
    String image;

    public Product(String name, String brand, double price) {
        this.id = Product.getNextId();
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    public static int getNextId() {
        return Product.NEXT_ID++;
    }

}
