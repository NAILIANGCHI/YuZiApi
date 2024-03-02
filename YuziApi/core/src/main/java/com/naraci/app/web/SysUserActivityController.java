package com.naraci.app.web;

import com.naraci.app.domain.SysUser;
import com.naraci.app.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ShenZhaoYu
 * @date 2024/3/2
 */
@RestController
@RequestMapping("/activity")
public class SysUserActivityController {
    @Autowired
    private SysUserService sysUserService;

    @Operation(summary = "获取用户详情")
    @PostMapping("/userinfo")
    public SysUser userInfo() {
        return sysUserService.userInfo();
    }
}

