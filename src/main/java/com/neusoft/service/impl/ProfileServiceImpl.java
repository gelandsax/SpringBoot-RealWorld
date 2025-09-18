package com.neusoft.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neusoft.mapper.FollowsMapper;
import com.neusoft.mapper.UsersMapper;
import com.neusoft.model.Follows;
import com.neusoft.model.Users;
import com.neusoft.service.IProfileService;
import com.neusoft.service.IUserService;
import com.neusoft.vo.ProfileDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProfileServiceImpl implements IProfileService {
    @Resource
    UsersMapper usersMapper;
    @Resource
    FollowsMapper followsMapper;
    @Override
    public ProfileDetail followUser(String username,int loginUserId) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Users followee = usersMapper.selectOne(wrapper);
        Follows follow = new Follows();
        follow.setFollowerId(loginUserId);
        follow.setFolloweeId(followee.getId());
        followsMapper.insert(follow);

        ProfileDetail profileDetail = new ProfileDetail();
        profileDetail.setFollowing(true);
        profileDetail.setBio(followee.getBio());
        profileDetail.setImage(followee.getImage());
        profileDetail.setUsername(followee.getUsername());
        return profileDetail;
    }

    @Override
    public ProfileDetail unFollowUser(String username,int loginUserId) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Users followee = usersMapper.selectOne(wrapper);

        QueryWrapper<Follows> wrapperFollow = new QueryWrapper<>();
        wrapperFollow.eq("followee_id",followee.getId()).eq("follower_id",loginUserId);
        followsMapper.delete(wrapperFollow);
        ProfileDetail profileDetail = new ProfileDetail();
        profileDetail.setFollowing(false);
        profileDetail.setBio(followee.getBio());
        profileDetail.setImage(followee.getImage());
        profileDetail.setUsername(followee.getUsername());
        return profileDetail;
    }

}
