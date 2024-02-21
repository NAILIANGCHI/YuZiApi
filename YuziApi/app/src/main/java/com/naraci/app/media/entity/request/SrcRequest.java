package com.naraci.app.media.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author ShenZhaoYu
 * @date 2024/2/20
 */
@Data
public class SrcRequest {

    @Schema(description = "链接")
    private String url;
}
