package com.banmingi.communityplus.contentcenter.dto.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @auther 半命i 2020/5/14
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListDTO implements Serializable {
    private static final long serialVersionUID = 5724800208820239735L;
    /**
     *文章id
     */
    private Integer id;

    /**
     *作者id
     */
    private Integer userId;

    /**
     * 分类id
     */
    private Integer categoryId;


    /**
     * 作者头像
     */
    private String avatarUrl;

    /**
     * 分类图像
     */
    private String categoryImage;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 阅览数
     */
    private Integer viewCount;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 收藏数
     */
    private Integer collectionCount;

    /**
     * 是否原创: 0:原创，1: 转载
     */
    private Integer isOriginal;


    /**
     * 发布时间
     */
    private Long createTime;

}
