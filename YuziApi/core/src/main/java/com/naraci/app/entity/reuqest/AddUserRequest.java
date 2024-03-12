package com.naraci.app.entity.reuqest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.Data;

/**
 * @author ShenZhaoYu
 * @date 2024/2/23
 */
@Data
public class AddUserRequest {
    @Schema(description = "邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;
    @Schema(description = "用户名")
    private String name;

    @Schema(description = "密码")
    private String passwd;

    @Schema(description = "性别")
    private Integer gender;

    @Schema(description = "角色")
    private String role;


}
