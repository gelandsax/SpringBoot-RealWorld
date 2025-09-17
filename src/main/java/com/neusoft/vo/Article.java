package com.neusoft.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.models.auth.In;

import java.util.Date;
import java.util.List;

public class Article {
    private String slug;
    private String title;
    private String description;
    private String body;
    @JsonIgnore
    private String[] tag_arr;

    public String[] getTag_arr() {
        return tag_arr;
    }

    public void setTag_arr(String[] tag_arr) {
        this.tag_arr = tag_arr;
    }

    public boolean isFavorited() {
        return favorited;
    }

    private List<String> tagList;
    private Date createdAt;
    private Date updatedAt;
    private boolean favorited;
    private int favoritesCount;
    private Author author;
    public Article(){}
    public void setSlug(String slug) {
        this.slug = slug;
    }
    public String getSlug() {
        return slug;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getBody() {
        return body;
    }
    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }
    public List<String> getTagList() {
        return tagList;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }
    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }
    public boolean getFavorited() {
        return favorited;
    }
    public void setFavoritesCount(int favoritesCount) {
        this.favoritesCount = favoritesCount;
    }
    public int getFavoritesCount() {
        return favoritesCount;
    }
    public void setAuthor(Author author) {
        this.author = author;
    }
    public Author getAuthor() {
        return author;
    }
}
