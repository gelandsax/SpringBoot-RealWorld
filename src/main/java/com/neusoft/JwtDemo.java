package com.neusoft;

import com.neusoft.model.Users;
import com.neusoft.utils.JwtService;

public class JwtDemo {
    public static void main(String[] args) {
        Users user = new Users();
        user.setId(12);

        JwtService jwtDemo = new JwtService();
        String Token = jwtDemo.toToken(user);
        System.out.println(Token);
    }
}
