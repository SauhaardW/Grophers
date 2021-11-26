package com.example.project;


import java.util.ArrayList;
import java.util.Date;

public class Order {
    Customer customer;
    Store store;
    ArrayList<Product> products;
    Date date;
    double total;
    String status;

    public Order() {}

    public Order(Customer customer, Store store, ArrayList<Product> products, Date date, String status) {
        this.customer = customer;
        this.store = store;
        this.products = products;
        this.date = date;
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
