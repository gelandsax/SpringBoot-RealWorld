package com.neusoft.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neusoft.dto.ArticleQueryCondition;
import com.neusoft.dto.NewArticle;
import com.neusoft.mapper.ArticleTagsMapper;
import com.neusoft.mapper.ArticlesMapper;
import com.neusoft.mapper.TagsMapper;
import com.neusoft.mapper.UsersMapper;
import com.neusoft.model.ArticleTags;
import com.neusoft.model.Articles;
import com.neusoft.model.Tags;
import com.neusoft.model.Users;
import com.neusoft.service.IArticleService;
import com.neusoft.vo.Article;
import com.neusoft.vo.Author;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleServiceImpl implements IArticleService {
    @Resource
    TagsMapper tagsMapper;
    @Resource
    ArticlesMapper articlesMapper;
    @Resource
    ArticleTagsMapper articleTagsMapper;
    @Resource
    UsersMapper usersMapper;
    @Override
    public Article createArticle(NewArticle newarticle) {
        Articles article = new Articles();
        article.setTitle(newarticle.getTitle());
        article.setDescription(newarticle.getDescription());
        UUID uuid = UUID.randomUUID();
        article.setSlug(uuid.toString());
        article.setBody(newarticle.getBody());
        article.setAuthorId(1);
        articlesMapper.insert(article);

        ProcessTags(newarticle, article);
        Author author = new Author();
        Users user=usersMapper.selectById(article.getAuthorId());
        author.setUsername(user.getUsername());
        author.setBio(user.getBio());
        author.setImage(user.getImage());
        author.setFollowing(false);


        Article newArticle = new Article();
        newArticle.setTitle(article.getTitle());
        newArticle.setDescription(article.getDescription());
        newArticle.setBody(article.getBody());
        newArticle.setAuthor(author);
        newArticle.setSlug(article.getSlug());

        Articles dbarticle = articlesMapper.selectById(article.getId());
        newArticle.setCreatedAt(dbarticle.getCreatedAt());
        newArticle.setUpdatedAt(dbarticle.getUpdatedAt());

        newArticle.setTagList(newarticle.getTagList());
        newArticle.setFavorited(false);
        int favoritesCount = articlesMapper.getFavoritesCountBySlug(article.getSlug());
        newArticle.setFavoritesCount(favoritesCount);
        return newArticle;
    }
    @Override
    public Article getArticleBySlug(String slug) {
        QueryWrapper<Articles> qw = new QueryWrapper<>();
        qw.eq("slug", slug);
        Articles articles = articlesMapper.selectOne(qw);

        Article article = new Article();
        if(articles != null){
            article.setTitle(articles.getTitle());
            article.setSlug(articles.getSlug());
            article.setDescription(articles.getDescription());
            article.setBody(articles.getBody());
            article.setCreatedAt(articles.getCreatedAt());
            article.setUpdatedAt(articles.getUpdatedAt());
            List<String> tags = articlesMapper.getTagsByArticleSlug(slug);
            article.setTagList(tags);
            article.setFavorited(false);

            Author author = new Author();
            Users user=usersMapper.selectById(articles.getAuthorId());
            author.setUsername(user.getUsername());
            author.setBio(user.getBio());
            author.setImage(user.getImage());
            author.setFollowing(false);
            article.setAuthor(author);

            int favoritesCount = articlesMapper.getFavoritesCountBySlug(slug);
            article.setFavoritesCount(favoritesCount);

        }


        return article;
    }
    @Override
    public Article updateArticle(String slug, NewArticle updateArticle) {
        QueryWrapper<Articles> qw = new QueryWrapper<>();
        qw.eq("slug", slug);
        Articles article = articlesMapper.selectOne(qw);

        article.setTitle(updateArticle.getTitle());
        article.setDescription(updateArticle.getDescription());
        article.setBody(updateArticle.getBody());
        article.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        QueryWrapper<ArticleTags> qw2 = new QueryWrapper<>();
        qw2.eq("article_id", article.getId());
        articleTagsMapper.delete(qw2);

        ProcessTags(updateArticle, article);

        articlesMapper.updateById(article);

        Author author = new Author();
        Users user=usersMapper.selectById(article.getAuthorId());
        author.setUsername(user.getUsername());
        author.setBio(user.getBio());
        author.setImage(user.getImage());
        author.setFollowing(false);

        Article newArticle = new Article();
        newArticle.setTitle(updateArticle.getTitle());
        newArticle.setDescription(updateArticle.getDescription());
        newArticle.setBody(updateArticle.getBody());
        newArticle.setSlug(article.getSlug());
        newArticle.setCreatedAt(article.getCreatedAt());
        newArticle.setUpdatedAt(article.getUpdatedAt());
        newArticle.setAuthor(author);
        newArticle.setFavorited(false);
        newArticle.setFavoritesCount(articlesMapper.getFavoritesCountBySlug(slug));
        List<String> tags = articlesMapper.getTagsByArticleSlug(slug);
        newArticle.setTagList(tags);


        return newArticle;
    }

    @Override
    public List<Article> getArticles(ArticleQueryCondition articleQueryCondition) {
        List<Article> articles = articlesMapper.getArticles(articleQueryCondition);
        for(Article article : articles){
            List<String> tagList = new ArrayList<>();
            if(article.getTag_arr()[0] != null){
                tagList = Arrays.asList(article.getTag_arr());
            }
            article.setTagList(tagList);
        }
        return articles;
    }
    @Override
    public int getArticlesCount(ArticleQueryCondition articleQueryCondition) {
        return articlesMapper.getArticlesCount(articleQueryCondition);
    }
    public void ProcessTags(NewArticle updateArticle, Articles article) {
        for(String tag: updateArticle.getTagList()){
            QueryWrapper<Tags> qw1 = new QueryWrapper<>();
            qw1.eq("name", tag);
            Tags tags = tagsMapper.selectOne(qw1);
            if(tags == null){
                Tags newtag = new Tags();
                newtag.setName(tag);
                tagsMapper.insert(newtag);
                ArticleTags articleTags = new ArticleTags();
                articleTags.setArticleId(article.getId());
                articleTags.setTagId(newtag.getId());
                articleTagsMapper.insert(articleTags);
            }else{
                ArticleTags articleTags = new ArticleTags();
                articleTags.setArticleId(article.getId());
                articleTags.setTagId(tags.getId());
                articleTagsMapper.insert(articleTags);
            }
        }
    }
}
