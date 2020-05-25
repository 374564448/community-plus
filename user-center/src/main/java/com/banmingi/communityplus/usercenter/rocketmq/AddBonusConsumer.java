package com.banmingi.communityplus.usercenter.rocketmq;

import com.banmingi.communityplus.usercenter.dto.user.UserAddBonusMsgDTO;
import com.banmingi.communityplus.usercenter.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

/**
 * @auther 半命i 2020/5/17
 * @description 消费rocketMq中的消息
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddBonusConsumer {

    private final UserService userService;

    /**
     *为用户添加积分.
     * @param message 积分添加的实体信息
     */
    @StreamListener(Sink.INPUT)
    public void articleAddBonus(UserAddBonusMsgDTO message) {
        this.userService.addBonus(message);
    }

}
