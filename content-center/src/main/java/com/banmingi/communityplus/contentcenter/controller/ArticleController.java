package com.banmingi.communityplus.contentcenter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther 半命i 2020/5/13
 * @description
 */
@RestController
@RequestMapping("article")
public class ArticleController {
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
