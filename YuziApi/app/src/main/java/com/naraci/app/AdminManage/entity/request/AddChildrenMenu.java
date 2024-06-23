package com.naraci.app.AdminManage.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author ShenZhaoYu
 * @date 2024/6/22
 * @ 添加子类菜单
 */

@Data
public class AddChildrenMenu {

    @Schema(description = "父类id")
    private String mainId;

    @Schema(description = "菜单名")
    private String displayName;

    @Schema(description = "路径名")
    private String name;

}
