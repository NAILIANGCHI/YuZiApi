package com.naraci.app.media.web;

import com.naraci.app.media.entity.request.CheckTemplateRequest;
import com.naraci.app.media.entity.request.QuotationRequest;
import com.naraci.app.media.service.impl.WpsOnlineService;
import com.naraci.app.media.service.impl.WpsPageDataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


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
    public WpsPageDataResponse getAllData(
            @RequestParam int page,
            @RequestParam int pageSize
    ) throws IOException {
        return wpsOnlineService.requestAllData(page, pageSize);
    }

    @Operation(summary = "打印账单")
    @PostMapping("/check")
    public void pushWxRobot(
            @RequestBody CheckTemplateRequest request
            ){
        wpsOnlineService.pushWxRobot(request);
    }

    @Operation(summary = "报价图片单生成")
    @PostMapping("/quotation")
    public void quotation(
            @RequestBody QuotationRequest request
    ){
        wpsOnlineService.quotation(request);
    }
}
