package com.banmingi.communityplus.notificationcenter.rocketmq.input;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

/**
 * @author 半命i 2020/8/2
 * @description
 */

public interface CheckCodeSink {
    String CHECK_CODE_INPUT = "checkCode-input";

    @Input(CHECK_CODE_INPUT)
    SubscribableChannel checkCodeInput();
}
