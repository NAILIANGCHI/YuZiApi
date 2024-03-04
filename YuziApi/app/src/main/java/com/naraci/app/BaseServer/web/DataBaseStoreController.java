package com.naraci.app.BaseServer.web;

import com.naraci.app.BaseServer.service.DataBaseStoreService;
import com.naraci.app.media.entity.request.SrcRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author ShenZhaoYu
 * @date 2024/3/5
 */
@RestController
@Tag(name = "数据库资源爬取")
@RequestMapping("/captrue")
public class DataBaseStoreController {

    @Resource
    private DataBaseStoreService dataBaseStoreService;
    @Operation(summary = "图片资源存储")
    @PostMapping("/image/acg")
    public void imageAcg(@RequestBody SrcRequest request) throws IOException, InterruptedException {
        dataBaseStoreService.imageAcg(request);
    }
}
