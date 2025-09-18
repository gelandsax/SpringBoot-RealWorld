package com.neusoft.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neusoft.dto.ArticleQueryCondition;
import com.neusoft.dto.NewArticle;
import com.neusoft.mapper.*;
import com.neusoft.model.*;
import com.neusoft.service.IArticleService;
import com.neusoft.utils.UserUtils;
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
    @Resource
    FavoritesMapper favoritesMapper;
    @Resource
    FollowsMapper followsMapper;
    @Override
    public Article createArticle(NewArticle newarticle) {
        Articles article = new Articles();
        article.setTitle(newarticle.getTitle());
        article.setDescription(newarticle.getDescription());
        UUID uuid = UUID.randomUUID();
        article.setSlug(uuid.toString());
        article.setBody(newarticle.getBody());
//        article.setAuthorId(1);
        Users loginUser = UserUtils.getLoginUser();
        article.setAuthorId(loginUser.getId());
        articlesMapper.insert(article);

        ProcessTags(newarticle, article);
        Author author = new Author();
        Users user=usersMapper.selectById(article.getAuthorId());
        author.setUsername(user.getUsername());
        author.setBio(user.getBio());
        author.setImage(user.getImage());
//        author.setFollowing(false);
        QueryWrapper<Follows> queryFollowsWrapper = new QueryWrapper<>();
        queryFollowsWrapper.eq("follower_id", loginUser.getId()).eq("followee_id", user.getId());
        Integer followsCount = followsMapper.selectCount(queryFollowsWrapper);
        author.setFollowing(followsCount > 0);

        QueryWrapper<Favorites> queryFavoritesWrapper2 = new QueryWrapper<>();
        queryFavoritesWrapper2.eq("user_id", loginUser.getId())
                .eq("article_id", article.getId());
        Integer count = favoritesMapper.selectCount(queryFavoritesWrapper2);

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
//        newArticle.setFavorited(false);
        if(count > 0){
            newArticle.setFavorited(true);
        }
        else{
            newArticle.setFavorited(false);
        }
        int favoritesCount = articlesMapper.getFavoritesCountBySlug(article.getSlug());
        newArticle.setFavoritesCount(favoritesCount);
        return newArticle;
    }
    @Override
    public Article getArticleBySlug(String slug) {
        Users loginUser = UserUtils.getLoginUser();
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
//            article.setFavorited(false);
            QueryWrapper<Favorites> queryFavoritesWrapper2 = new QueryWrapper<>();
            queryFavoritesWrapper2.eq("user_id", loginUser.getId())
                    .eq("article_id", articles.getId());
            Integer count = favoritesMapper.selectCount(queryFavoritesWrapper2);
            if(count > 0){
                article.setFavorited(true);
            }
            else{
                article.setFavorited(false);
            }
            Author author = new Author();
            Users user=usersMapper.selectById(articles.getAuthorId());
            author.setUsername(user.getUsername());
            author.setBio(user.getBio());
            author.setImage(user.getImage());
//            author.setFollowing(false);
            QueryWrapper<Follows> queryFollowsWrapper = new QueryWrapper<>();
            queryFollowsWrapper.eq("follower_id", loginUser.getId()).eq("followee_id", user.getId());
            Integer followsCount = followsMapper.selectCount(queryFollowsWrapper);
            author.setFollowing(followsCount > 0);
            article.setAuthor(author);

            int favoritesCount = articlesMapper.getFavoritesCountBySlug(slug);
            article.setFavoritesCount(favoritesCount);

        }


        return article;
    }
    @Override
    public Article updateArticle(String slug, NewArticle updateArticle) {
        Users loginUser = UserUtils.getLoginUser();
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
        QueryWrapper<Follows> queryFollowsWrapper = new QueryWrapper<>();
        queryFollowsWrapper.eq("follower_id", loginUser.getId()).eq("followee_id", user.getId());
        Integer followsCount = followsMapper.selectCount(queryFollowsWrapper);
        author.setFollowing(followsCount > 0);

        Article newArticle = new Article();
        newArticle.setTitle(updateArticle.getTitle());
        newArticle.setDescription(updateArticle.getDescription());
        newArticle.setBody(updateArticle.getBody());
        newArticle.setSlug(article.getSlug());
        newArticle.setCreatedAt(article.getCreatedAt());
        newArticle.setUpdatedAt(article.getUpdatedAt());
        newArticle.setAuthor(author);
//        newArticle.setFavorited(false);
        QueryWrapper<Favorites> queryFavoritesWrapper2 = new QueryWrapper<>();
        queryFavoritesWrapper2.eq("user_id", loginUser.getId())
                .eq("article_id", article.getId());
        Integer count = favoritesMapper.selectCount(queryFavoritesWrapper2);
        if(count > 0){
            newArticle.setFavorited(true);
        }
        else{
            newArticle.setFavorited(false);
        }
        newArticle.setFavoritesCount(articlesMapper.getFavoritesCountBySlug(slug));
        List<String> tags = articlesMapper.getTagsByArticleSlug(slug);
        newArticle.setTagList(tags);


        return newArticle;
    }

    @Override
    public List<Article> getArticles(ArticleQueryCondition articleQueryCondition) {
        Users loginUser = UserUtils.getLoginUser();
        if (loginUser == null) {
            articleQueryCondition.setLogin_userId(0);
        }
        else{
            articleQueryCondition.setLogin_userId(loginUser.getId());
        }
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
        Users loginUser = UserUtils.getLoginUser();
        if (loginUser == null) {
            articleQueryCondition.setLogin_userId(0);
        }
        else{
            articleQueryCondition.setLogin_userId(loginUser.getId());
        }
        return articlesMapper.getArticlesCount(articleQueryCondition);
    }
    @Override
    public void deleteArticle(String slug) {
        QueryWrapper<Articles> qw = new QueryWrapper<>();
        qw.eq("slug", slug);
        Articles article = articlesMapper.selectOne(qw);
        if(article != null){
            articlesMapper.deleteById(article.getId());
        }
        else{
            throw new RuntimeException("文章不存在");
        }
    }
    @Override
    public List<Article> feedArticles(ArticleQueryCondition articleQueryCondition) {
        List<Article> feedArticles = articlesMapper.feedArticles(articleQueryCondition);
        for(Article article : feedArticles){
            List<String> tagList = new ArrayList<>();
            if(article.getTag_arr()[0] != null){
                tagList = Arrays.asList(article.getTag_arr());
            }
            article.setTagList(tagList);
        }
        return feedArticles;
    }
    @Override
    public int getFeedArticlesCount(ArticleQueryCondition articleQueryCondition) {
        return articlesMapper.getFeedArticlesCount(articleQueryCondition);
    }
    @Override
    public Article favoriteArticle(int articleId,int UserId) {
        QueryWrapper<Favorites> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId).eq("user_id", UserId);
        Favorites exist = favoritesMapper.selectOne(wrapper);
        if (exist == null) {
            Favorites favorites = new Favorites();
            favorites.setArticleId(articleId);
            favorites.setUserId(UserId);
            favoritesMapper.insert(favorites);
        }

        Articles articles = articlesMapper.selectById(articleId);
        Article article = new Article();
        article.setTitle(articles.getTitle());
        article.setDescription(articles.getDescription());
        article.setBody(articles.getBody());
        article.setSlug(articles.getSlug());
        article.setCreatedAt(articles.getCreatedAt());
        article.setUpdatedAt(articles.getUpdatedAt());
        article.setFavorited(true);
        article.setFavoritesCount(articlesMapper.getFavoritesCountBySlug(articles.getSlug()));
        List<String> tags = articlesMapper.getTagsByArticleSlug(article.getSlug());
        article.setTagList(tags);
        Author author = new Author();
        int authorID = articles.getAuthorId();

        Users user = usersMapper.selectById(authorID);
        author.setBio(user.getBio());
        author.setUsername(user.getUsername());
        author.setImage(user.getImage());
        Follows follows = new Follows();
        QueryWrapper<Follows> followsQueryWrapper = new QueryWrapper<>();
        followsQueryWrapper.eq("followee_id", authorID).eq("follower_id", UserId);
        follows = followsMapper.selectOne(followsQueryWrapper);
        if(follows != null){
            author.setFollowing(true);
        }
        else{
            author.setFollowing(false);
        }
        article.setAuthor(author);
        return article;
    }

    @Override
    public Article UnFavoriteArticle(int articleId, int userId) {
        // 1. 删除用户的喜欢记录
        QueryWrapper<Favorites> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId)
                .eq("user_id", userId);
        favoritesMapper.delete(wrapper);

        // 2. 查询文章信息
        Articles articles = articlesMapper.selectById(articleId);
        if (articles == null) {
            return null; // 或抛异常，根据项目需求
        }

        // 3. 构建返回的 Article 对象
        Article article = new Article();
        article.setTitle(articles.getTitle());
        article.setDescription(articles.getDescription());
        article.setBody(articles.getBody());
        article.setSlug(articles.getSlug());
        article.setCreatedAt(articles.getCreatedAt());
        article.setUpdatedAt(articles.getUpdatedAt());
        List<String> tags = articlesMapper.getTagsByArticleSlug(article.getSlug());
        article.setTagList(tags);

        // 4. 更新收藏状态
        article.setFavorited(false);
        article.setFavoritesCount(articlesMapper.getFavoritesCountBySlug(articles.getSlug()));

        // 5. 构建作者信息
        Author author = new Author();
        int authorId = articles.getAuthorId();
        Users user = usersMapper.selectById(authorId);
        author.setBio(user.getBio());
        author.setUsername(user.getUsername());
        author.setImage(user.getImage());

        // 6. 查询当前登录用户是否关注作者
        QueryWrapper<Follows> followsQueryWrapper = new QueryWrapper<>();
        followsQueryWrapper.eq("followee_id", authorId)
                .eq("follower_id", userId);
        Follows follows = followsMapper.selectOne(followsQueryWrapper);
        author.setFollowing(follows != null);

        article.setAuthor(author);

        return article;
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
