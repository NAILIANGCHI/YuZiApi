package com.naraci.app.media.web;

import com.naraci.app.media.entity.request.SrcRequest;
import com.naraci.app.media.entity.response.DouyinImageResponse;
import com.naraci.app.media.entity.response.DouyinVideoResponse;
import com.naraci.app.media.service.MediaService;
import com.naraci.core.aop.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.ConnectException;

import java.net.SocketTimeoutException;
import java.util.List;

/**
 * @author ShenZhaoYu
 * @date 2024/2/20
 */
@RestController
@Tag(name = "音频媒体解析")
@RequestMapping("/jx")
public class MediaController {

    @Resource
    private MediaService mediaService;

    @SneakyThrows
    @Operation(summary = "抖音无水印视频解析")
    @PostMapping("/douyin/video")
    public DouyinVideoResponse douyinVideo(
            @RequestBody SrcRequest url) {
        try {
            return mediaService.douyinVideo(url);
        } catch (ConnectException | SocketTimeoutException e) {
            throw new CustomException("处理超时，请反馈管理员");
        } catch (ClassCastException e) {
            throw new CustomException("解析失败，输入的链接可能不合法");
        }
    }

    @Operation(summary = "抖音图片解析")
    @PostMapping("/douyin/image")
    public DouyinImageResponse douyinImage(
            @RequestBody SrcRequest url
    ) {
        return mediaService.douyinImage(url);
    }
}
