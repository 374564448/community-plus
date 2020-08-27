package com.banmingi.communityplus.cloudcenter.dto.oss;

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
public class FileInfoDTO implements Serializable {
    private static final long serialVersionUID = 4493355279972817178L;
    /**
     * 文件路径
     */
    private String path;
}
