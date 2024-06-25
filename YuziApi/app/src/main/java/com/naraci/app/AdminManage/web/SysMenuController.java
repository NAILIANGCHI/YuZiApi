package com.naraci.app.AdminManage.web;

import com.naraci.app.AdminManage.domain.SysChildrenMenu;
import com.naraci.app.AdminManage.domain.SysMenu;
import com.naraci.app.AdminManage.entity.request.AddChildrenMenu;
import com.naraci.app.AdminManage.entity.request.AddMenu;
import com.naraci.app.AdminManage.entity.response.MenuRouter;
import com.naraci.app.AdminManage.service.SysChildrenMenuService;
import com.naraci.app.AdminManage.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Resource
    private SysChildrenMenuService sysChildrenMenuService;

    @Operation(summary = "添加菜单")
    @PostMapping("/add")
    public void addMenu(
            @RequestBody @Valid AddMenu request
            ) {
        sysMenuService.addMenu(request);
    }

    @Operation(summary = "获取父菜单列表")
    @GetMapping("/getMenuList")
    public List<SysMenu> getMenuList() {
        return sysMenuService.getMenuList();
    }

    @Operation(summary = "更新菜单开关")
    @PostMapping("/update/MenuCheck/{id}")
    public void updateMenCheck(
            @PathVariable String id
    ) {
        sysMenuService.updateMenCheck(id);
    }

    @Operation(summary = "删除菜单")
    @PostMapping("/delMenu/{id}")
    public void delMenu(
            @PathVariable String id
    ) {
        sysMenuService.delMenu(id);
    }

    @Operation(summary = "添加子菜单")
    @PostMapping("/children/addMenu")
    public void addChildrenMenu(
            @RequestBody AddChildrenMenu request
            ) {
        sysChildrenMenuService.addChildrenMenu(request);
    }

    @Operation(summary = "更新子菜单那开关")
    @PostMapping("/update/childrenMenuCheck/{id}")
    public void updateChildrenMenuCheck(
            @PathVariable String id
    ) {
        sysChildrenMenuService.updateChildrenMenuCheck(id);
    }

    @Operation(summary = "删除子菜单")
    @PostMapping("/delChildrenMenu/{id}")
    public void delChildrenMenu(
            @PathVariable String id
    ) {
        sysChildrenMenuService.delChildrenMenu(id);
    }

    @Operation(summary = "获取子菜单列表")
    @PostMapping("/getChildrenMenuList/{id}")
    public List<SysChildrenMenu> getChildrenMenuList(
            @PathVariable @Schema(description = "父类菜单id") String id
    ) {
       return sysChildrenMenuService.getMenuList(id);
    }

    @Operation(summary = "获取菜单导航")
    @GetMapping("/router")
    public List<MenuRouter> getMenuRouter() {
        return sysMenuService.getMenuRouter();
    }
}
