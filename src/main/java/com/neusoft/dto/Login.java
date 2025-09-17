package com.neusoft.dto;

public class Login {
    private UserLogin user;

    public void setUser(UserLogin user) {
        this.user = user;
    }
    public UserLogin getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Login{" +
                "user=" + user +
                '}';
    }
}
