package com.neusoft.controller;

import com.neusoft.model.Users;
import com.neusoft.response.ApiResponse;
import com.neusoft.service.IProfileService;
import com.neusoft.utils.UserUtils;
import com.neusoft.vo.ProfileDetail;
import com.neusoft.vo.ProfileVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProfileController {
    @Autowired
    IProfileService profileService;
    @PostMapping("/profiles/{username}/follow")
    public ApiResponse<ProfileVo> followUser(@PathVariable String username) {
        UserUtils userUtils = new UserUtils();
        Users loginUser = userUtils.getLoginUser();
        int loginUserId = loginUser.getId();
        ProfileDetail profileDetail = profileService.followUser(username, loginUserId);
        ProfileVo profileVo = new ProfileVo();
        profileVo.setProfile(profileDetail);
        return ApiResponse.success(profileVo);
    }
    @DeleteMapping("/profiles/{username}/follow")
    public ApiResponse<ProfileVo> unFollowUser(@PathVariable String username){
        UserUtils userUtils = new UserUtils();
        Users loginUser = userUtils.getLoginUser();
        int loginUserId = loginUser.getId();
        ProfileDetail profileDetail = profileService.unFollowUser(username, loginUserId);
        ProfileVo profileVo = new ProfileVo();
        profileVo.setProfile(profileDetail);
        return ApiResponse.success(profileVo);
    }
}
