package com.banmingi.communityplus.contentcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @auther 半命i 2020/5/12
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "article")
public class Article {

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
     * 是否原创: 0:原创，1: 转载
     */
    private Integer isOriginal;

    /**
     * 审核状态：0: 待审核  1:审核通过 2:审核不通过
     */
    private Integer status;

    /**
     * 审核不通过原因
     */
    private String rejectReason;

    /**
     *是否公开：0: 公开 1: 私密
     */
    private Integer showFlag;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

}
