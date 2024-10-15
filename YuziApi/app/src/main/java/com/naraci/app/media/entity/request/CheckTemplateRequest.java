package com.naraci.app.media.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data

public class CheckTemplateRequest {
    @Schema(description = "日期")
    private Data dataNow;

    @Schema(description = "客户代码")
    private String clientCode;

    @Schema(description = "运单号")
    private String waybill;

    @Schema(description = "费用项目")
    private String costItem;

    @Schema(description = "SKU数量")
    private String costItem;

    @Schema(description = "计费重量")
    private String costItem;

    @Schema(description = "单价(元)")
    private String costItem;

    @Schema(description = "金额(元)")
    private String costItem;

    @Schema(description = "上架费(元)")
    private String putAway;

    @Schema(description = "报关(元)")
    private String clearance;

    @Schema(description = "税点")
    private String taxPoint;
    @Schema(description = "保险(元)")
    private String insurance;

    @Schema(description = "杂费(元)")
    private String incidentals;

    @Schema(description = "合计(元)")
    private String total;

    @Schema(description = "类别")
    private String type;

}
