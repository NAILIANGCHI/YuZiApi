package com.naraci.app.AdminManage.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author ShenZhaoYu
 * @date 2024/6/12
 * @添加菜单
 */

@Data
public class AddMenu {
    @Schema(description = "菜单名")
    private String displayName;

    @Schema(description = "路径名")
    private String name;

    @Schema(description = "图标")
    private String icon;

}
