package com.banmingi.communityplus.contentcenter.rocketmq;

import com.alibaba.fastjson.JSON;
import com.banmingi.communityplus.contentcenter.dto.article.ArticleAuditDTO;
import com.banmingi.communityplus.contentcenter.entity.RocketMQTransactionLog;
import com.banmingi.communityplus.contentcenter.mapper.RocketMQTransactionLogMapper;
import com.banmingi.communityplus.contentcenter.service.ArticleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

/**
 * @auther 半命i 2020/5/16
 * @description 为用户添加积分的本地事务.
 */
@RocketMQTransactionListener(txProducerGroup = "AddBonusTransaction-Group")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AddBonusTransactionListener implements RocketMQLocalTransactionListener {

    private final ArticleService articleService;
    private final RocketMQTransactionLogMapper rocketMQTransactionLogMapper;


    /**
     * 执行本地事务.
     *
     * @param message 送半消息时构建的 message
     * @param o       发送半消息的参数 arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        //消息头
        MessageHeaders headers = message.getHeaders();
        //获取 transactionId
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        //获取articleId get的时候获取到的是字符串
        Integer articleId = Integer.valueOf((String) headers.get("articleId"));
        //获取articleAuditDTO
        String auditDTOStr = (String) headers.get("articleAuditDTO");
        ArticleAuditDTO articleAuditDTO
                = JSON.parseObject(auditDTOStr, ArticleAuditDTO.class);

        try {
            //执行本地事务
            this.articleService.auditInDBWithRocketMQLog(articleId, articleAuditDTO, transactionId);
            //本地事务执行成功
            log.info("文章审核通过并为用户添加积分,本地事务执行成功");
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.error("文章审核通过并为用户添加积分,本地事务执行失败: {}", e.getMessage());
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 检查本地事务的状态.
     * 这里通过查询事务日志表实现
     *
     * @param message 送半消息时构建的 message
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        //消息头
        MessageHeaders headers = message.getHeaders();
        //获取 transactionId
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        //查询数据库是否有该条事务日志
        QueryWrapper<RocketMQTransactionLog> wrapper = new QueryWrapper<>();
        wrapper.eq("transaction_id", transactionId);
        RocketMQTransactionLog rocketMQTransactionLog = this.rocketMQTransactionLogMapper.selectOne(wrapper);
        if (rocketMQTransactionLog != null) {
            return RocketMQLocalTransactionState.COMMIT;
        }
        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
