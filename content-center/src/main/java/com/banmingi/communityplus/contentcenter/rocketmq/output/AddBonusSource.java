package com.banmingi.communityplus.contentcenter.rocketmq.output;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

/**
 * @author 半命i 2020/8/2
 * @description
 */
public interface AddBonusSource {
    String ADD_BONUS_OUTPUT = "addBonus-output";

    @Output(ADD_BONUS_OUTPUT)
    MessageChannel addBonusOutput();
}
