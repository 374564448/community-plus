package com.banmingi.communityplus.commons.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 半命i 2020/6/9
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionBody {
    private String body;
    private int status;
}
