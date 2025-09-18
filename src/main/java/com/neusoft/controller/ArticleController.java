package com.neusoft.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neusoft.dto.ArticleQueryCondition;
import com.neusoft.dto.CreateArticle;
import com.neusoft.mapper.ArticlesMapper;

import com.neusoft.model.Articles;
import com.neusoft.model.Users;
import com.neusoft.response.ApiResponse;
import com.neusoft.service.IArticleService;

import com.neusoft.utils.UserUtils;
import com.neusoft.vo.Article;
import com.neusoft.vo.MultipleArticlesVo;
import com.neusoft.vo.SingleArticleVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ArticleController {
    @Autowired
    IArticleService iArticleService;
    @Resource
    ArticlesMapper articlesMapper;
    @PostMapping("/articles")
    public ApiResponse<SingleArticleVo> CreateArticle(@RequestBody CreateArticle newarticle) {
        Article article = iArticleService.createArticle(newarticle.getArticle());
        SingleArticleVo vo = new SingleArticleVo();
        vo.setArticle(article);
        return ApiResponse.success(vo);
    }
    @GetMapping("/articles/{slug}")
    public ApiResponse<SingleArticleVo> GetArticle(@PathVariable String slug){
        Article article = iArticleService.getArticleBySlug(slug);
        SingleArticleVo vo = new SingleArticleVo();
        vo.setArticle(article);
        return ApiResponse.success(vo);
    }
    @PutMapping("/articles/{slug}")
    public ApiResponse<SingleArticleVo> UpdateArticle(@PathVariable String slug, @RequestBody CreateArticle updateArticle){
        Article article = iArticleService.updateArticle(slug, updateArticle.getArticle());
        SingleArticleVo vo = new SingleArticleVo();
        vo.setArticle(article);
        return ApiResponse.success(vo);
    }
    @GetMapping("/articles")
    public ApiResponse<MultipleArticlesVo> GetArticles(ArticleQueryCondition articleQueryCondition){
        if(articleQueryCondition.getLimit() == null){
            articleQueryCondition.setLimit(20);
        }
        if(articleQueryCondition.getOffset() == null){
            articleQueryCondition.setOffset(0);
        }
        List<Article> articles = iArticleService.getArticles(articleQueryCondition);
        MultipleArticlesVo vo = new MultipleArticlesVo();
        vo.setArticles(articles);
        vo.setArticlesCount(iArticleService.getArticlesCount(articleQueryCondition));
        return ApiResponse.success(vo);
    }
    @GetMapping("/articles/feed")
    public ApiResponse<MultipleArticlesVo> FeedArticle(ArticleQueryCondition articleQueryCondition){
        if(articleQueryCondition.getLimit() == null){
            articleQueryCondition.setLimit(20);
        }
        if(articleQueryCondition.getOffset() == null){
            articleQueryCondition.setOffset(0);
        }
        Users LoginUser = UserUtils.getLoginUser();
        int loginUserId = LoginUser.getId();
        articleQueryCondition.setLogin_userId(loginUserId);
        List<Article> articles = iArticleService.feedArticles(articleQueryCondition);
        MultipleArticlesVo vo = new MultipleArticlesVo();
        vo.setArticles(articles);
        vo.setArticlesCount(iArticleService.getFeedArticlesCount(articleQueryCondition));
        return ApiResponse.success(vo);
    }

    @DeleteMapping("/articles/{slug}")
    public ApiResponse DeleteArticle(@PathVariable String slug){
        iArticleService.deleteArticle(slug);
        return ApiResponse.success();
    }
    @PostMapping("/articles/{slug}/favorite")
    public ApiResponse<Article> favouriteArticle(@PathVariable String slug){
        QueryWrapper<Articles> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("slug", slug);
        Articles articles = articlesMapper.selectOne(queryWrapper);
        int articleId = articles.getId();
        Users LoginUser = UserUtils.getLoginUser();
        int loginUserId = LoginUser.getId();
        Article article = iArticleService.favoriteArticle(articleId, loginUserId);
        SingleArticleVo vo = new SingleArticleVo();
        vo.setArticle(article);
        return ApiResponse.success(vo);
    }
    @DeleteMapping("/articles/{slug}/favorite")
    public ApiResponse<Article> UnfavouriteArticle(@PathVariable String slug){
        QueryWrapper<Articles> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("slug", slug);
        Articles articles = articlesMapper.selectOne(queryWrapper);
        int articleId = articles.getId();
        Users LoginUser = UserUtils.getLoginUser();
        int loginUserId = LoginUser.getId();
        Article article = iArticleService.UnFavoriteArticle(articleId, loginUserId);
        SingleArticleVo vo = new SingleArticleVo();
        vo.setArticle(article);
        return ApiResponse.success(vo);
    }
}
