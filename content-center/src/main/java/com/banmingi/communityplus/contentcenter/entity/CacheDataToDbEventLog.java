package com.banmingi.communityplus.contentcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 半命i 2020/5/25
 * @description 文章缓存数据日志实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "cache_data_to_db_event_log")
public class CacheDataToDbEventLog implements Serializable {
    private static final long serialVersionUID = -4349469063228775483L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 数据的id
     */
    private Integer dataId;

    /**
     * 事件
     */
    private String event;

    /**
     * 事件描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Long createTime;

}
