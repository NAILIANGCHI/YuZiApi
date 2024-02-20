package com.naraci.app.entity.reuqest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author ShenZhaoYu
 * @date 2024/2/17
 */
@Data
public class LoginRequest {
    @Email
    @Schema(description = "邮箱")
    private String email;

    @NotNull(message = "密码不能为空")
    @Schema(description = "密码")
    private String password;

    @NotNull(message = "验证码不能为空")
    @Schema(description = "验证码" )
    private String code;

}
