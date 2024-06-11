package com.naraci.app.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.naraci.core.util.IdUtil;
import lombok.Data;

/**
 *
 * @TableName user_role
 */
@TableName(value ="user_role")
@Data
public class UserRole implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id = IdUtil.getSnowflakeId();

    /**
     * 是否删除
     */
    private Boolean isDeleted;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 角色id
     */
    private String roleId;

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

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
