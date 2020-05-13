package com.banmingi.communityplus.contentcenter.service;

import com.banmingi.communityplus.contentcenter.entity.Category;
import com.banmingi.communityplus.contentcenter.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther 半命i 2020/5/13
 * @description
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryService {

    private final CategoryMapper categoryMapper;

    /**
     * 获取所有分类集合
     * @return
     */
    public List<Category> queryAllCategory() {
        return this.categoryMapper.selectList(null);
    }
}
