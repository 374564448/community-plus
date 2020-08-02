package com.banmingi.communityplus.usercenter.rocketmq.output;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author 半命i 2020/8/2
 * @description
 */
public interface CheckCodeSource {
    String CHECK_CODE_OUTPUT = "checkCode-output";

    @Output(CHECK_CODE_OUTPUT)
    MessageChannel output();
}
