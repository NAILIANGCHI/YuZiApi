package com.naraci.app.media.web;

import com.naraci.app.media.entity.response.WpsAllDataResponse;
import com.naraci.app.media.service.impl.WpsOnlineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author ShenZhaoYu
 * @date 2024/10/13
 * @描述
 */
@RestController
@Tag(name = "WPS数据请求")
@RequestMapping("/wps")
public class WpsOnlineController {

    @Autowired
    private WpsOnlineService wpsOnlineService;
    @Operation(summary = "获取全部数据")
    @GetMapping("/all")
    public List<WpsAllDataResponse> acgImage() throws IOException {
        return wpsOnlineService.requestAllData();
    }

    @Operation(summary = "打印账单")
    @PostMapping("/check")
    public void checkPdf(

    ){

    }
}
