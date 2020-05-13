package com.banmingi.communityplus.contentcenter.controller;

import com.banmingi.communityplus.contentcenter.entity.Category;
import com.banmingi.communityplus.contentcenter.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther 半命i 2020/5/13
 * @description
 */
@RestController
@RequestMapping("category")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 获取所有分类集合
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Category>> queryAllCategory() {
        List<Category> categoryList = this.categoryService.queryAllCategory();
        if (CollectionUtils.isEmpty(categoryList)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryList);
    }
}
