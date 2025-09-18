package com.neusoft.service;

import com.neusoft.vo.ProfileDetail;
import com.neusoft.vo.ProfileVo;

public interface IProfileService {
    ProfileDetail followUser(String username,int loginUserId);
    ProfileDetail unFollowUser(String username,int loginUserId);
}
