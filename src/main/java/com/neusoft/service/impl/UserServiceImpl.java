package com.neusoft.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neusoft.dto.UserLogin;
import com.neusoft.mapper.UsersMapper;
import com.neusoft.model.Users;
import com.neusoft.service.IUserService;
import com.neusoft.utils.JwtService;
import com.neusoft.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    JwtService jwtService;
    @Resource
    UsersMapper usersMapper;
    @Override
    public User userLogin(UserLogin user) {

        QueryWrapper<Users> qw = new QueryWrapper<>();
        qw.eq("email", user.getEmail());
        Users dbUser = usersMapper.selectOne(qw);
        if (dbUser == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!dbUser.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        User userLogin = new User();
        userLogin.setEmail(dbUser.getEmail());
        String Token = jwtService.toToken(dbUser);
        userLogin.setToken(Token);
        userLogin.setBio(dbUser.getBio());
        userLogin.setUsername(dbUser.getUsername());
        userLogin.setImage(dbUser.getImage());
        return userLogin;
    }
}
