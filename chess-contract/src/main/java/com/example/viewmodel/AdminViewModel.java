package com.example.viewmodel;

public class AdminViewModel {
    private String username;

    public AdminViewModel(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
