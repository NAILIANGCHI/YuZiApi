package com.naraci.app.media.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ShenZhaoYu
 * @date 2024/10/20
 * @描述
 */
@Data
public class QuotationRequest {
    @Schema(description = "客户代码")
    private String customerCode;

    @Schema(description = "运单号")
    private String trackingNumber;

    @Schema(description = "品名")
    private String itemName;

    @Schema(description = "目的地")
    private String destination;

    @Schema(description = "产品分类")
    private String productCategory;

    @Schema(description = "件数")
    private Integer quantity;

    @Schema(description = "重量kg")
    private Double weight;

    @Schema(description = "体积m³")
    private Double volume;

    @Schema(description = "密度")
    private BigDecimal density;

    @Schema(description = "货值")
    private Double value;

    @Schema(description = "保险费")
    private Double insuranceFee;

    @Schema(description = "提货费")
    private Double pickupFee;

    @Schema(description = "上架费单价")
    private Double shelvingUnitPrice;

    @Schema(description = "上架费")
    private Double shelvingFee;

    @Schema(description = "动态列数据")
    private List<DynamicRow> dynamicRows;

    @Schema(description = "固定费用总计")
    private Double totalFixedCost;

}

