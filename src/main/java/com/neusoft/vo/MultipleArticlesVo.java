package com.neusoft.vo;

import com.neusoft.model.Articles;

import java.util.List;

public class MultipleArticlesVo {
    private List<Article> articles;
    private int articlesCount;

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
    public List<Article> getArticles() {
        return articles;
    }
    public void setArticlesCount(int articlesCount) {
        this.articlesCount = articlesCount;
    }
    public int getArticlesCount() {
        return articlesCount;
    }
}
