package com.banmingi.communityplus.usercenter.rocketmq.input;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author 半命i 2020/8/2
 * @description
 */
public interface AddBonusSink {
    String ADD_BONUS_INPUT = "addBonus-input";

    @Input(ADD_BONUS_INPUT)
    SubscribableChannel addBonusInput();
}
