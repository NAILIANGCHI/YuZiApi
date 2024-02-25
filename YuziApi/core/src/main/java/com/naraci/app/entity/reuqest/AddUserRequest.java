package com.naraci.app.entity.reuqest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author ShenZhaoYu
 * @date 2024/2/23
 */
@Data
public class AddUserRequest {
    @Schema(description = "用户名")
    private String name;

    @Schema(description = "密码")
    private String passwd;

    @Schema(description = "角色")
    private String role;


}
