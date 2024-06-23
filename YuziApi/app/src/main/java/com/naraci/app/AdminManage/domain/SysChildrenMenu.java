package com.naraci.app.AdminManage.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.naraci.core.source.Snowflake;
import com.naraci.core.util.IdUtil;
import lombok.Data;

/**
 * 
 * @TableName sys_children_menu
 */
@TableName(value ="sys_children_menu")
@Data
public class SysChildrenMenu implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private String id = IdUtil.getSnowflakeId();

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime = new Date();

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)

    private Date updateTime = new Date();

    /**
     * 父类id
     */
    private String mainId;

    /**
     * 是否启用
     */
    private Boolean isStart;

    /**
     * 菜单展示名字
     */
    private String displayName;

    /**
     * 菜单名
     */
    private String name;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}