package com.neusoft.service;

import com.neusoft.dto.ArticleQueryCondition;
import com.neusoft.dto.NewArticle;
import com.neusoft.model.Articles;
import com.neusoft.vo.Article;
import com.neusoft.vo.SingleArticleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IArticleService {
    Article createArticle(NewArticle newarticle);
    Article getArticleBySlug(String slug);
    Article updateArticle(String slug, NewArticle updateArticle);
    List<Article> getArticles(ArticleQueryCondition articleQueryCondition);
    List<Article> feedArticles(ArticleQueryCondition articleQueryCondition);
    int getArticlesCount(ArticleQueryCondition articleQueryCondition);
    int getFeedArticlesCount(ArticleQueryCondition articleQueryCondition);
    void deleteArticle(String slug);
    Article favoriteArticle(int articleId,int UserId);
    Article UnFavoriteArticle(int articleId,int UserId);
}
