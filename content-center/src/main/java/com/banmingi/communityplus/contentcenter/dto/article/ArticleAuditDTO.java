package com.banmingi.communityplus.contentcenter.dto.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @auther 半命i 2020/5/16
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleAuditDTO implements Serializable {


    private static final long serialVersionUID = 7789768481662683912L;
    /**
     * 审核状态
     */
    private String articleStatus;

    /**
     * 原因
     */
    private String reason;

}
