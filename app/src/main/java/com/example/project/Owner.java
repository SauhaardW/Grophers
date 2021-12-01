package com.example.project;

public class Owner extends User{
    private int storeId;
    String image;

    public Owner() {}

    public Owner(String email, String username) {
        this.setEmail(email);
        this.setUsername(username);
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}