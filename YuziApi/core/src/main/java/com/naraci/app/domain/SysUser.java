package com.naraci.app.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.naraci.core.util.IdUtil;
import lombok.Data;

/**
 *
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
public class SysUser implements Serializable {
    /**
     * id
     */
    @TableId
    private String id = IdUtil.getSnowflakeId();

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    /**
     * 名字
     */
    private String name;

    /**
     * 性别0是男 1是女
     */
    private Integer gender;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 手机号
     */
    private Integer mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否删除
     */
    private Boolean isDeleted;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
