package com.neusoft.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.neusoft.model.Comments;
import com.neusoft.vo.Comment;

import java.util.List;

public interface CommentsMapper extends BaseMapper<Comments> {
    List<Comment> getCommentsByArticleId(int articleId,int loginUserId);
}
