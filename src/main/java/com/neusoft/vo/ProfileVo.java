package com.neusoft.vo;


import org.springframework.context.annotation.Profile;

public class ProfileVo {
    private ProfileDetail profile;

    public void setProfile(ProfileDetail profile) {
        this.profile = profile;
    }
    public ProfileDetail getProfile() {
        return profile;
    }

}
