package com.banmingi.communityplus.contentcenter.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "rocketmq_transaction_log")
public class RocketMQTransactionLog implements Serializable {

    private static final long serialVersionUID = 2343070443039843533L;
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 事务id
     */
    private String transactionId;

    /**
     * 日志
     */
    private String log;
}