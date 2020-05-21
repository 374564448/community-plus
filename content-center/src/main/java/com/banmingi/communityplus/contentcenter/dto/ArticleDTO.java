package com.banmingi.communityplus.contentcenter.dto;

import com.banmingi.communityplus.contentcenter.dto.usercenter.UserDTO;
import com.banmingi.communityplus.contentcenter.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 半命i 2020/5/21
 * @description 文章详情
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO implements Serializable {
    private static final long serialVersionUID = 8062056659419618481L;

    /**
     * id
     */
    private Integer id;

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
     * 是否原创: 1:原创，2: 转载
     */
    private Integer isOriginal;

    /**
     * 修改时间
     */
    private Long modifyTime;

    /**
     * 分类
     */
    private Category category;

    /**
     * 作者
     */
    private UserDTO userDTO;

}
