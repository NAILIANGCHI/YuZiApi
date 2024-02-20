package com.naraci.app.entity.response;

import com.naraci.app.domain.SysUser;
import lombok.Data;

/**
 * @author ShenZhaoYu
 * @date 2024/2/17
 */
@Data
public class LoginResponse {
    private SysUser user;

    private String token;
}
