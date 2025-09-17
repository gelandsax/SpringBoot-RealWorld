package com.neusoft.vo;

public class Author {
    private String username;
    private String bio;
    private String image;
    private boolean following;
    public Author(){}
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getBio() {
        return bio;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getImage() {
        return image;
    }
    public void setFollowing(boolean following) {
        this.following = following;
    }
    public boolean getFollowing() {
        return following;
    }
}
