package com.example.project;

import java.util.ArrayList;

public class Customer extends User{
    ArrayList<Order> orders;
    ArrayList<Product> cart;

    public Customer() {}

    public Customer(String email, String username) {
        this.setEmail(email);
        this.setUsername(username);
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public ArrayList<Product> getCart() {
        return cart;
    }

    public void setCart(ArrayList<Product> cart) {
        this.cart = cart;
    }
}