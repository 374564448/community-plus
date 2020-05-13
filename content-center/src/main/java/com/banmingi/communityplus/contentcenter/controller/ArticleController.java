package com.banmingi.communityplus.contentcenter.controller;

import com.banmingi.communityplus.contentcenter.dto.ArticlePublishDTO;
import com.banmingi.communityplus.contentcenter.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther 半命i 2020/5/13
 * @description
 */
@RestController
@RequestMapping("article")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleController {

    private final ArticleService articleService;
    /**
     * 发布文章.
     * @return
     */
    @PostMapping("/publish")
    public ResponseEntity<Void> publish(@RequestBody ArticlePublishDTO articlePublishDTO) {
        this.articleService.publishOrUpdate(articlePublishDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 保存文章 暂时保存7天
     * @param articlePublishDTO
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody ArticlePublishDTO articlePublishDTO) {
        this.articleService.save(articlePublishDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * 获取保存的文章
     * @param userId
     * @return
     */
    @GetMapping("/getTheSavedArticle")
    public ResponseEntity<ArticlePublishDTO> getTheSavedArticle(@RequestParam("userId") Integer userId) {
        ArticlePublishDTO theSavedArticle = this.articleService.getTheSavedArticle(userId);
        return ResponseEntity.ok(theSavedArticle);
    }
}
