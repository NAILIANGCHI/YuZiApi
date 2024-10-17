package com.naraci.app.media.service.impl;

import com.naraci.app.media.entity.response.WpsAllDataResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author ShenZhaoYu
 * @date 2024/10/17
 * @描述
 */
@Data
public class WpsPageDataResponse {
    @Schema(description = "总记录数")
    private Integer totalRecords;

    @Schema(description = "总页数")
    private Integer totalPages;

    @Schema(description = "起始索引")
    private Integer startIndex;

    @Schema(description = "结束索引")
    private Integer endIndex;

    @Schema(description = "分页数据")
    private List<WpsAllDataResponse> pageData;

}
