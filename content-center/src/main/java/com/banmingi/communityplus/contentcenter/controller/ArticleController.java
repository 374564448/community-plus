package com.banmingi.communityplus.contentcenter.controller;

import com.banmingi.communityplus.auth.annotations.CheckLogin;
import com.banmingi.communityplus.contentcenter.dto.article.ArticleDTO;
import com.banmingi.communityplus.contentcenter.dto.article.ArticleListDTO;
import com.banmingi.communityplus.contentcenter.dto.article.ArticlePublishDTO;
import com.banmingi.communityplus.contentcenter.service.ArticleService;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("articles")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 文件文章id查找文章
     * @param id 文章id
     * @return 文章详情实体
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> findById(@PathVariable Integer id) {
        ArticleDTO articleDTO = this.articleService.findById(id);
        if (articleDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(articleDTO);
    }

    /**
     *
     * @param search 搜索条件
     * @param categoryId 分类
     * @param sort 排序方式
     * @param pageNum 页号
     * @param pageSize 一页的文章条数
     * @return 文章列表
     */
    @GetMapping("/q")
    public ResponseEntity<PageInfo<ArticleListDTO>> q(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false,defaultValue = "new") String sort,
            @RequestParam(required = false,defaultValue = "1") Integer pageNum,
            @RequestParam(required = false,defaultValue = "10") Integer pageSize) {
        PageInfo<ArticleListDTO> articleList = this.articleService.q(search,categoryId,sort,pageNum,pageSize);

        return ResponseEntity.ok(articleList);
    }

    /**
     * 发布文章.
     * @return 成功
     */
    @PostMapping("/publish")
    @CheckLogin
    public ResponseEntity<Void> publish(@RequestBody ArticlePublishDTO articlePublishDTO) {
        this.articleService.publishOrUpdate(articlePublishDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 保存文章 暂时保存7天.
     * @param articlePublishDTO 文章发布实体
     * @return 成功
     */
    @PostMapping("/save")
    @CheckLogin
    public ResponseEntity<Void> save(@RequestBody ArticlePublishDTO articlePublishDTO) {
        this.articleService.save(articlePublishDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * 获取保存的文章
     * @param userId 用户id
     * @return 返回保存的文章
     */
    @GetMapping("/save/{userId}")
    @CheckLogin
    public ResponseEntity<ArticlePublishDTO> getTheSavedArticle(@PathVariable("userId") Integer userId) {
        ArticlePublishDTO theSavedArticle = this.articleService.getTheSavedArticle(userId);
        return ResponseEntity.ok(theSavedArticle);
    }
}
