package com.example.project;

public class Owner extends User{
    private int storeId;

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
}
