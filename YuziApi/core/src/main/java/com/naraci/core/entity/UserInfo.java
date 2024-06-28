package com.naraci.core.entity;

import com.naraci.app.domain.Role;
import com.naraci.app.domain.SysUser;
import lombok.Data;

/**
 * @author ShenZhaoYu
 * @date 2024/2/17
 */
@Data
public class UserInfo {

    /**
     * 用户信息
     */
    private SysUser sysUser;

    /**
     * 角色信息
     */
    private Role role;
}
