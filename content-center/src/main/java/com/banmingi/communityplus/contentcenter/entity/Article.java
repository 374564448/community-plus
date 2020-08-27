package com.banmingi.communityplus.contentcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @auther 半命i 2020/5/12
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "article")
public class Article implements Serializable {
    private static final long serialVersionUID = -8820885615804786524L;
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
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
     * 审核状态：NOT_YET: 待审核  PASS:审核通过 REJECT:审核不通过
     */
    private String auditStatus;

    /**
     * 审核不通过原因
     */
    private String reason;

    /**
     *是否公开：1: 公开 2: 私密
     */
    private Integer showFlag;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long modifyTime;

}
