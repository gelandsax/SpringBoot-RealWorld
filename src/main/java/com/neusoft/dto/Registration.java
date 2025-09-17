package com.neusoft.dto;

public class Registration {
    private UserReg user;

    public void setUser(UserReg user) {
        this.user = user;
    }
    public UserReg getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "user=" + user +
                '}';
    }
}
