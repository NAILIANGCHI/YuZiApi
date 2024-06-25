package com.naraci.app.AdminManage.entity.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author ShenZhaoYu
 * @date 2024/6/25
 * @子菜单Dto
 */
@Data
public class ChildrenMenu {

    @Schema(description = "路由路径")
    private String key;

    @Schema(description = "标题名字")
    private String label;
}
