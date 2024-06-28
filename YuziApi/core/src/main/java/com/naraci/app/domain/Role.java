package com.naraci.app.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.naraci.app.entity.enums.RoleEnum;
import lombok.Data;

/**
 *
 * @TableName role
 */
@TableName(value ="role")
@Data
public class Role implements Serializable {
    /**
     * 唯一id
     */
    @TableId
    private String id;

    /**
     * 是否删除
     */
    private Boolean isDeleted;

    /**
     * 角色
     */
    private RoleEnum.RoleEnums role;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 是否有全局权限
     */
    private Boolean key;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
