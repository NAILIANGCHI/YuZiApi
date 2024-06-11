package com.naraci.app.BaseServer.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.naraci.core.util.IdUtil;
import lombok.Data;

/**
 * 
 * @TableName sys_menu
 */
@TableName(value ="sys_menu")
@Data
public class SysMenu implements Serializable {
    /**
     * id
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
     * 菜单展示名字
     */
    private String displayName;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}