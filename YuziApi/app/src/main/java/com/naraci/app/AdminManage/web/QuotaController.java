package com.naraci.app.AdminManage.web;

import com.naraci.app.AdminManage.service.QuoTaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ShenZhaoYu
 * @date 2024/3/3
 */
@RestController
@Tag(name = "额度设置")
@RequestMapping("quota")
public class QuotaController {
    @Autowired
    private QuoTaService quoTaService;

    @Operation(summary = "管理员手动增加抖音次数")
    @PostMapping("/douyin/{email}")
    public void addDouYinCount(
            @PathVariable @Email(message = "邮箱格式不正确")String email
    ) {
        quoTaService.addDouyin(email);
    }
}
