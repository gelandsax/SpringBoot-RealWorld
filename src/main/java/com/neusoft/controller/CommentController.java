package com.neusoft.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neusoft.dto.CommentDto;
import com.neusoft.dto.NewComment;
import com.neusoft.mapper.ArticlesMapper;
import com.neusoft.mapper.CommentsMapper;
import com.neusoft.mapper.UsersMapper;
import com.neusoft.model.Articles;
import com.neusoft.model.Comments;
import com.neusoft.model.Users;
import com.neusoft.response.ApiResponse;
import com.neusoft.service.IArticleService;
import com.neusoft.service.ICommentService;
import com.neusoft.utils.UserUtils;
import com.neusoft.vo.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    IArticleService iArticleService;
    @Resource
    ArticlesMapper articlesMapper;
    @Resource
    CommentsMapper commentsMapper;
    @Resource
    UsersMapper usersMapper;
    @Autowired
    ICommentService iCommentService;
    @GetMapping("/articles/{slug}/comments")
    public ApiResponse<MuiltpleCommentsVo> getMuiltpleComments(@PathVariable String slug) {
       QueryWrapper<Articles> queryWrapper = new QueryWrapper<>();
       queryWrapper.eq("slug", slug);
       Articles articles = articlesMapper.selectOne(queryWrapper);
       int artcleId = articles.getId();
       UserUtils userUtils = new UserUtils();
       Users LoginUser = userUtils.getLoginUser();
       int userId = LoginUser.getId();
       List<Comment> comments = commentsMapper.getCommentsByArticleId(artcleId, userId);
       MuiltpleCommentsVo muiltpleCommentsVo = new MuiltpleCommentsVo();
       muiltpleCommentsVo.setComments(comments);
        return ApiResponse.success(muiltpleCommentsVo);

    }
    @PostMapping("/articles/{slug}/comments")
    public ApiResponse<SingleCommentVo> AddComment(@PathVariable String slug, @RequestBody CommentDto Comment) {
        UserUtils userUtils = new UserUtils();
        Users LoginUser = userUtils.getLoginUser();
        int userId = LoginUser.getId();
        // 1. 根据 slug 查询文章
        Articles article = articlesMapper.selectOne(new QueryWrapper<Articles>().eq("slug", slug));

        // 2. 构建 Comments 实体
        NewComment newComment = Comment.getComment();
        Comments comments = new Comments();
        comments.setBody(newComment.getBody());
        comments.setArticleId(article.getId());
        comments.setAuthorId(userId);
        comments.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        comments.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        // 3. 插入数据库
        commentsMapper.insert(comments);

        // 4. 查询作者信息
        Users authorUser = usersMapper.selectById(userId);
        Author author = new Author();
        author.setUsername(authorUser.getUsername());
        author.setBio(authorUser.getBio());
        author.setImage(authorUser.getImage());
        author.setFollowing(false);

        // 5. 封装 SingleCommentVo
        SingleCommentVo vo = new SingleCommentVo();
        Comment comment = new Comment();
        comment.setBody(comments.getBody());
        comment.setAuthor(author);
        comment.setId(comments.getId());
        comment.setCreatedAt(comments.getCreatedAt());
        comment.setUpdatedAt(comments.getUpdatedAt());
        vo.setComment(comment);

        return ApiResponse.success(vo);

    }
    @DeleteMapping("/articles/{slug}/comments/{id}")
    public ApiResponse deleteComment(@PathVariable String slug, @PathVariable int id) {
       commentsMapper.deleteById(id);
       return ApiResponse.success();

    }
}
