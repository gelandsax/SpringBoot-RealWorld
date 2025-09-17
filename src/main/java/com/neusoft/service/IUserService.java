package com.neusoft.service;

import com.neusoft.dto.UserLogin;
import com.neusoft.vo.User;

public interface IUserService {
    User userLogin(UserLogin user);
}
