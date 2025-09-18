package com.neusoft.service.impl;

import com.neusoft.mapper.CommentsMapper;
import com.neusoft.service.ICommentService;
import com.neusoft.vo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {
    @Resource
    CommentsMapper commentsMapper;
    @Override
    public List<Comment> getCommentByArticleId(int ArticleId, int LoginUserId) {
        return commentsMapper.getCommentsByArticleId(ArticleId,LoginUserId);
    }
}
