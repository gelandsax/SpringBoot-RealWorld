package com.neusoft.controller;

import com.neusoft.mapper.TagsMapper;
import com.neusoft.model.Tags;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
public class HelloController {
    @Resource
    TagsMapper tagsMapper;
    @GetMapping("/hello")
    public List<Tags> hello(){
       List<Tags> tags = tagsMapper.selectList(null);
       return tags;
    }

}
