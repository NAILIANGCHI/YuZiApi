package com.naraci.app.web;

import com.naraci.app.domain.SysUser;
import com.naraci.app.service.SysUserService;
import com.naraci.core.entity.UserInfo;
import com.naraci.core.util.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ShenZhaoYu
 * @date 2024/3/2
 */
@RestController
@Tag(name = "用户服务配置")
@RequestMapping("/activity")
public class SysUserActivityController {
    @Autowired
    private SysUserService sysUserService;

    @Operation(summary = "获取用户详情")
    @PostMapping("/userinfo")
    public SysUser userInfo(
            @RequestAttribute(JwtUtils.TOKEN)UserInfo userInfo
            ) {
        return sysUserService.userInfo(userInfo);
    }
}

