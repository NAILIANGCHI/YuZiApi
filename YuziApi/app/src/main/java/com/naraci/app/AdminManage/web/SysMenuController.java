package com.naraci.app.AdminManage.web;

import com.naraci.app.AdminManage.entity.request.AddMenu;
import com.naraci.app.AdminManage.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ShenZhaoYu
 * @date 2024/6/11
 * @描述
 */
@RestController
@Tag(name = "菜单管理")
@RequestMapping("/menu")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    @Operation(summary = "添加菜单")
    @PostMapping("/add")
    public void addMenu(
            @RequestBody @Valid AddMenu request
            ) {
        sysMenuService.addMenu(request);
    }



}
