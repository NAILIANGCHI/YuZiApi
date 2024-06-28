package com.naraci.app.AdminManage.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.naraci.core.util.IdUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


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
     * 是否开启
     */
    private Boolean isStart;

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