package com.example.user;

public class UserRequest {

    private String username;
    private String email;
    private String password;

    public UserRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() { return email;
    }
    public String getPassword(){
        return password;
    }
}
