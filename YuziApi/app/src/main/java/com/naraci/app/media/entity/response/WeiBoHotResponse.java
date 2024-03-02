package com.naraci.app.media.entity.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ShenZhaoYu
 * @date 2024/3/3
 */
@Data
public class WeiBoHotResponse {
    @Schema(description = "排名")
    private String rank;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "热度")
    private String hot;

    @Schema(description = "上榜时间")
    private LocalDateTime time;

}
