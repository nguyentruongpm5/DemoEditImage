package com.example.demoeditimage.model;

public class User {
    private int id;
    private String username;
    private String email;
    private String phone;
    private int success;
    private String token;


    public User(int id, String username, String email, String phone, int success, String token) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.success = success;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }
}
