package com.neusoft.service;

import com.neusoft.vo.Comment;

import java.util.List;

public interface ICommentService {
    List<Comment> getCommentByArticleId (int ArticleId, int LoginUserId);
}
