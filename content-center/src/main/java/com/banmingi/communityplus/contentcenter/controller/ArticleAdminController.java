package com.banmingi.communityplus.contentcenter.controller;

import com.banmingi.communityplus.contentcenter.dto.article.ArticleAuditDTO;
import com.banmingi.communityplus.contentcenter.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther 半命i 2020/5/16
 * @description
 */
@RestController
@RequestMapping("admin/articles")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleAdminController {

    private final ArticleService articleService;

    /**
     *审核文章
     * @param id 文章id
     * @param articleAuditDTO dto
     * @return
     */
    @PutMapping("/audit/{id}")
    public ResponseEntity<Void> audit(@PathVariable Integer id,
                                      @RequestBody ArticleAuditDTO articleAuditDTO) {
        this.articleService.audit(id,articleAuditDTO);
        return ResponseEntity.noContent().build();
    }


}
