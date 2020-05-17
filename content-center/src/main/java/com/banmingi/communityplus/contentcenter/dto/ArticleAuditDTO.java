package com.banmingi.communityplus.contentcenter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @auther 半命i 2020/5/16
 * @description
 */
@Getter
@AllArgsConstructor
public class ArticleAuditDTO implements Serializable {


    private static final long serialVersionUID = 7789768481662683912L;
    /**
     * 审核状态
     */
    private Integer articleStatus;

    /**
     * 原因
     */
    private String reason;

}
