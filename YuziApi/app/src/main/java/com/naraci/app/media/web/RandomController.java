package com.naraci.app.media.web;

import com.naraci.app.media.service.impl.RandomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ShenZhaoYu
 * @date 2024/3/6
 */
@RestController
public class RandomController {
    @Autowired
    private RandomService randomService;

    @Operation(summary = "随机图片(最多返回10条)")
    @GetMapping("/acgimge/{number}")
    public List<String> acgImage(
            @PathVariable @Schema(description = "输入要返的条数") Integer number
    ) {
        return randomService.acgImage(number);
    }
}
