package com.example.demoeditimage.model.param;

public class UserParam {
    private String username;
    private String phone;
    private String email;
    private String password;
    private String token;
    private String newPasword;
    private String confirmPassword;


    public UserParam(String username, String phone, String email, String password,String token, String newPasword, String confirmPassword) {
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getNewPasword() {
        return newPasword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }
}
