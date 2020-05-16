package com.banmingi.communityplus.contentcenter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @auther 半命i 2020/5/16
 * @description
 */
@Getter
@AllArgsConstructor
public class ArticleAuditDTO {

    /**
     * 审核状态
     */
    private Integer articleStatus;

    /**
     * 原因
     */
    private String reason;

}
