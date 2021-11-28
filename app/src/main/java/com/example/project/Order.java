package com.example.project;


import java.util.ArrayList;
import java.util.Date;

public class Order {
    String customerId;
    String storeId;
    ArrayList<CartItem> cart;
    long timestamp;
    String status;

    public Order() {}

    public Order(String customerId, String storeId, ArrayList<CartItem> cart, long timestamp, String status) {
        this.customerId = customerId;
        this.storeId = storeId;
        this.cart = cart;
        this.timestamp = timestamp;
        this.status = status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public ArrayList<CartItem> getCart() {
        return cart;
    }

    public void setCart(ArrayList<CartItem> cart) {
        this.cart = cart;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
