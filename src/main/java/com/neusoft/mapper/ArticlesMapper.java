package com.neusoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neusoft.dto.ArticleQueryCondition;
import com.neusoft.model.Articles;
import com.neusoft.vo.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticlesMapper extends BaseMapper<Articles> {
    List<String> getTagsByArticleSlug(@Param("slug") String slug);
    int getFavoritesCountBySlug(@Param("slug") String slug);
    List<Article> getArticles(ArticleQueryCondition articleQueryCondition);
    List<Article> feedArticles(ArticleQueryCondition articleQueryCondition);
    int getArticlesCount(ArticleQueryCondition articleQueryCondition);
    int getFeedArticlesCount(ArticleQueryCondition articleQueryCondition);

}
