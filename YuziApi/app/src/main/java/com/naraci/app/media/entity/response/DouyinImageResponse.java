package com.naraci.app.media.entity.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author ShenZhaoYu
 * @date 2024/2/22
 */
@Data
public class DouyinImageResponse {
    @Schema(description = "标题")
    private String title;

    @Schema(description = "图片列表")
    private List<String> images;
}
