package com.example.project;

public class CartItem extends Product{
    int quantity;

    public CartItem() {}

    public CartItem(Product product, int quantity) {
        this.id = product.getId();
        this.name = product.getName();
        this.brand = product.getBrand();
        this.price = product.getPrice();
        this.quantity = quantity;
        this.image = product.getImage();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
