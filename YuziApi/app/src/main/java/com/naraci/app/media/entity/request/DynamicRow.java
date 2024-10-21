package com.naraci.app.media.entity.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author ShenZhaoYu
 * @date 2024/10/20
 * @描述
 */
@Data
@Schema(description = "动态列")
public class DynamicRow {

    @Schema(description = "索引")
    private Integer index;

    @Schema(description = "时效")
    private String transitTime;

    @Schema(description = "运费单价")
    private Double freightUnitPrice;

    @Schema(description = "打包费")
    private Double packingFee;

    @Schema(description = "木架木托费用")
    private Double palletFee;

    @Schema(description = "木箱费用")
    private Double crateFee;

    @Schema(description = "总费用")
    private Double TotalCost;
}
