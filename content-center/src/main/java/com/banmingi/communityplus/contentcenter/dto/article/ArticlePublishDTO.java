package com.banmingi.communityplus.contentcenter.dto.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @auther 半命i 2020/5/13
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePublishDTO implements Serializable {

    private static final long serialVersionUID = 2185770001875311285L;
    /**
     * 文章id
     */
    private Integer id;
    /**
     * 作者id
     */
    private Integer userId;

    /**
     * 类别id
     */
    private Integer categoryId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章标签
     */
    private String tag;

    /**
     * 是否原创: 0:原创，1: 转载
     */
    private Integer isOriginal;


    /**
     *是否公开：0: 公开 1: 私密
     */
    private Integer showFlag;

}
