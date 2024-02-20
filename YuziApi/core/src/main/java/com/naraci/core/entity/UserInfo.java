package com.naraci.core.entity;

import com.naraci.core.util.IdUtil;
import lombok.Data;

import java.util.Date;

/**
 * @author ShenZhaoYu
 * @date 2024/2/17
 */
@Data
public class UserInfo {
    /**
     * id
     */
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
    private String password;
}
