package com.naraci.app.AdminManage.entity.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ShenZhaoYu
 * @date 2024/6/25
 * @ 路由菜单返回类
 */

@Data
public class MenuRouter {
//    @Schema(description = "菜单id")
//    private String id;

    @Schema(description = "路由路径")
    private String key;

    @Schema(description = "标题名字")
    private String label;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "子菜单")
    private List<ChildrenMenu> children = new ArrayList<>();
}
