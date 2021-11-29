package com.example.project;

import java.util.ArrayList;

public class Store {
    int id;
    String name;
    String hours;
    String image;
    Owner owner;
    ArrayList<Product> products;

    public Store() {}

    public Store(String name, String hours, String image, Owner owner, int id) {
        this.id = id;
        this.name = name;
        this.hours = hours;
        this.image = image;
        this.owner = owner;
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

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
