package com.naraci.app.media.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ShenZhaoYu
 * @date 2024/10/13
 * @描述
 */
@Data
public class CheckTemplateRequest {

    @Schema(description = "客户代码")
    private String customerCode;

    @Schema(description = "入库单号")
    private String warehousingNumber;

    @Schema(description = "SKU总数")
    private Integer skuTotalCount;

    @Schema(description = "原缠后总重量（kg）")
    private BigDecimal originalWeightAfterWrapping;    // 改为 BigDecimal

    @Schema(description = "客户单价（￥）")
    private BigDecimal customerUnitPrice;               // 改为 BigDecimal

    @Schema(description = "客户运费（￥）")
    private BigDecimal customerFreight;                 // 改为 BigDecimal

    @Schema(description = "客户上架费（￥）")
    private BigDecimal customerShelvingFee;             // 改为 BigDecimal

    @Schema(description = "客户杂费（￥）")
    private BigDecimal customerMiscellaneousFees;       // 改为 BigDecimal

    @Schema(description = "保险费（￥）")
    private BigDecimal insuranceFee;                     // 改为 BigDecimal

    @Schema(description = "提货费(￥)")
    private BigDecimal goodCostGet;

    @Schema(description = "备注说明")
    private String remarks;

    @Schema(description = "客户头程账单合计（￥）")
    private BigDecimal customerInitialBillingTotal;     // 改为 BigDecimal

    @Schema(description = "负责人")
    private String principal;
}