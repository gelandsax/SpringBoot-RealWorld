package com.neusoft.dto;

import java.util.List;

public class NewArticle {
    private String title;
    private String description;
    private String body;
    private List<String> tagList;

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
}
