package com.naraci.app.entity.reuqest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * @author ShenZhaoYu
 * @date 2024/2/14
 */
@Data
public class SysUserRegisterRequest {
    @NotBlank(message = "姓名不能为空")
    @Schema(description = "姓名")
    private String name;

    @Schema(description = "邮箱")
    @Email
    private String email;

    @Schema(description = "密码")
    @NotBlank(message = "手机号不能为空")
    private String password;

    @Schema(description = "性别")
    private Integer gender;
}
