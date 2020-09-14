package com.banmingi.communityplus.contentcenter.dto.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 半命i
 * @date 2020/09/13 23:03
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileArticleListDTO implements Serializable {
    private static final long serialVersionUID = 2871092472762444710L;
    /**
     *文章id
     */
    private Integer id;

    /**
     * 分类id
     */
    private Integer categoryId;


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
     *是否公开：1: 公开 2: 私密
     */
    private Integer showFlag;


    /**
     * 发布时间
     */
    private Long createTime;
}
