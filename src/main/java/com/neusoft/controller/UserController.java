package com.neusoft.controller;

import com.neusoft.dto.Login;
import com.neusoft.dto.Registration;
import com.neusoft.mapper.UsersMapper;
import com.neusoft.model.Users;
import com.neusoft.response.ApiResponse;
import com.neusoft.service.IUserService;
import com.neusoft.utils.JwtService;
import com.neusoft.vo.LoginUserVo;
import com.neusoft.vo.RegUserVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.neusoft.vo.User;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class UserController {
    @Resource
    JwtService jwtService;
    @Autowired
    IUserService iuserService;
    @Resource
    UsersMapper usersMapper;
    @PostMapping("/users")
    public ApiResponse<RegUserVo> regUser(@RequestBody Registration reg) {

    Users newUser = new Users();
    newUser.setEmail(reg.getUser().getEmail());
    newUser.setUsername(reg.getUser().getUsername());
    newUser.setPassword(reg.getUser().getPassword());
    usersMapper.insert(newUser);

    String token = jwtService.toToken(newUser);

    RegUserVo vo = new RegUserVo();
    User user = new User();
    user.setEmail(newUser.getEmail());
    user.setUsername(newUser.getUsername());
    user.setBio(newUser.getBio());
    user.setToken(token);
    user.setImage(newUser.getImage());
    vo.setUser(user);
    return ApiResponse.success(vo);
    }

    @PostMapping("/users/login")
    public ApiResponse<LoginUserVo> UserLogin(@RequestBody Login  login) {
        User currentUser = iuserService.userLogin(login.getUser());
        LoginUserVo vo = new LoginUserVo();
        vo.setUser(currentUser);
        return ApiResponse.success(vo);
    }
}
