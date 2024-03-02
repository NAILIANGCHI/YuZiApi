package com.naraci.app.media.web;

import com.naraci.app.media.entity.request.SrcRequest;
import com.naraci.app.media.entity.response.DouyinImageResponse;
import com.naraci.app.media.entity.response.DouyinVideoResponse;
import com.naraci.app.media.entity.response.WeiBoHotResponse;
import com.naraci.app.media.service.impl.MediaService;
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
    ) throws Exception {
        return mediaService.douyinImage(url);
    }

    @Operation(summary = "微博热搜前20")
    @GetMapping("weibo")
    public List<WeiBoHotResponse> weibo() throws IOException {
       return mediaService.weibo();
    }

//    @Operation(summary = "皮皮虾视频解析")
//    @PostMapping("/ppx")
//    public DouyinVideoResponse ppxVideo(
////            https://h5.pipix.com/bds/webapi/item/detail/?item_id=7340865145733650728
//            @RequestBody SrcRequest url
//    ) throws Exception {
//        return mediaService.douyinImage(url);
//    }
}
