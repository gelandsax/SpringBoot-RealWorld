package com.neusoft.controller;

import com.neusoft.mapper.TagsMapper;
import com.neusoft.model.Tags;
import com.neusoft.response.ApiResponse;
import com.neusoft.vo.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TagController {
    @Resource
    TagsMapper tagsMapper;
    @GetMapping("/api/tags")
    public ApiResponse<Tag> getTags(){
        Tag tag = new Tag();
        List<String> tags = new ArrayList<>();
        List <Tags> tagList = tagsMapper.selectList(null);
        for(Tags tagtmp : tagList){
            tags.add(tagtmp.getName());
        }
        tag.setTags(tags);
        return ApiResponse.success(tag);
    }

}
