package com.naraci.app.media.entity.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author ShenZhaoYu
 * @date 2024/2/22
 */
@Data
public class DouyinVideoResponse {
    @Schema(description = "视频标题")
    private String title;

    @Schema(description = "背景音乐")
    private String mp3;

    @Schema(description = "视频")
    private String mp4;
}
