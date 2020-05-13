package com.banmingi.communityplus.contentcenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther 半命i 2020/5/13
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "category")
public class Category {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 分类名
     */
    private String name;

}
