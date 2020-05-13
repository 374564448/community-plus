package com.banmingi.communityplus.cloudcenter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther 半命i 2020/5/13
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoDTO {
    /**
     * 文件路径
     */
    private String path;
}
