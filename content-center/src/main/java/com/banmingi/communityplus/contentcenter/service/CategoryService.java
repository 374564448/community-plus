package com.banmingi.communityplus.contentcenter.service;

import com.banmingi.communityplus.contentcenter.entity.Category;
import com.banmingi.communityplus.contentcenter.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther 半命i 2020/5/13
 * @description
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final RedisTemplate<String,Object> redisTemplate;

    private static final String ARTICLE_CATEGORY_KEY = "article:category";

    /**
     * 获取所有分类集合
     * @return
     */
    public List<Category> queryAllCategory() {
        List<Object> categoryList = this.redisTemplate.opsForList().range(ARTICLE_CATEGORY_KEY, 0, -1);

        //如果缓存中文章分类为空
        List<Category> categories;
        if (CollectionUtils.isEmpty(categoryList)) {
            categories = this.categoryMapper.selectList(null);
            categories.forEach(category -> {
                this.redisTemplate.opsForList().rightPush(ARTICLE_CATEGORY_KEY,category);
            });
            return categories;
        }
        categories = categoryList.stream()
                .map(category-> (Category)category).collect(Collectors.toList());

        return categories;
    }
}
