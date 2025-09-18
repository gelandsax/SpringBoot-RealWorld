package com.neusoft.vo;

import com.neusoft.model.Comments;

import java.util.List;

public class MuiltpleCommentsVo {
    private List<Comment> comments;

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    public List<Comment> getComments() {
        return comments;
    }
}
