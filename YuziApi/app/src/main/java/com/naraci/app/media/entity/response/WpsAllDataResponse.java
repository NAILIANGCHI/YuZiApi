package com.naraci.app.media.entity.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author ShenZhaoYu
 * @date 2024/10/13
 * @描述
 */
@Data
public class WpsAllDataResponse {
    @Schema(description = "序号")
    private Integer serialNumber;

    @Schema(description = "卖家发货日期")
    private String sellerShipmentDate;

    @Schema(description = "客户交货物流单号")
    private String customerDeliveryTrackingNumber;

    @Schema(description = "承运商物流单号")
    private String carrierTrackingNumber;

    @Schema(description = "中转仓入库日期")
    private String transferWarehouseInDate;

    @Schema(description = "中转发运日期")
    private String transferDispatchDate;

    @Schema(description = "莫斯科提货日期")
    private String moscowPickupDate;

    @Schema(description = "海外仓入库日期")
    private String overseasWarehouseInDate;

    @Schema(description = "订单状态")
    private String orderStatus;

    @Schema(description = "客户代码")
    private String customerCode;

    @Schema(description = "客户类型")
    private String customerType;

    @Schema(description = "客户名称")
    private String customerName;

    @Schema(description = "物流模式")
    private String logisticsMode;

    @Schema(description = "入库单号")
    private String warehousingNumber;

    @Schema(description = "商品名称")
    private String productName;

    @Schema(description = "品类")
    private String category;

    @Schema(description = "货值")
    private BigDecimal value;  // 改为 BigDecimal

    @Schema(description = "SKU总数")
    private Integer skuTotalCount;

    @Schema(description = "箱数")
    private Integer boxCount;

    @Schema(description = "客户包装总重量（kg）")
    private BigDecimal customerPackagingTotalWeight;  // 改为 BigDecimal

    @Schema(description = "客户包装总方数（m³）")
    private BigDecimal customerPackagingTotalVolume;   // 改为 BigDecimal

    @Schema(description = "原缠后总重量（kg）")
    private BigDecimal originalWeightAfterWrapping;    // 改为 BigDecimal

    @Schema(description = "原缠后总方数（m³）")
    private BigDecimal originalVolumeAfterWrapping;    // 改为 BigDecimal

    @Schema(description = "密度（原缠后）")
    private BigDecimal densityAfterWrapping;            // 改为 BigDecimal

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

    @Schema(description = "备注说明")
    private String remarks;

    @Schema(description = "客户头程账单合计（￥）")
    private BigDecimal customerInitialBillingTotal;     // 改为 BigDecimal

    @Schema(description = "客户付款日期")
    private String customerPaymentDate;

    // Setter 方法，用于设置保留两位小数
    public void setValue(double value) {
        this.value = BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }

    public void setCustomerPackagingTotalWeight(double customerPackagingTotalWeight) {
        this.customerPackagingTotalWeight = BigDecimal.valueOf(customerPackagingTotalWeight).setScale(2, RoundingMode.HALF_UP);
    }

    public void setCustomerPackagingTotalVolume(double customerPackagingTotalVolume) {
        this.customerPackagingTotalVolume = BigDecimal.valueOf(customerPackagingTotalVolume).setScale(2, RoundingMode.HALF_UP);
    }

    public void setOriginalWeightAfterWrapping(double originalWeightAfterWrapping) {
        this.originalWeightAfterWrapping = BigDecimal.valueOf(originalWeightAfterWrapping).setScale(2, RoundingMode.HALF_UP);
    }

    public void setOriginalVolumeAfterWrapping(double originalVolumeAfterWrapping) {
        this.originalVolumeAfterWrapping = BigDecimal.valueOf(originalVolumeAfterWrapping).setScale(2, RoundingMode.HALF_UP);
    }

    public void setDensityAfterWrapping(double densityAfterWrapping) {
        this.densityAfterWrapping = BigDecimal.valueOf(densityAfterWrapping).setScale(2, RoundingMode.HALF_UP);
    }

    public void setCustomerUnitPrice(double customerUnitPrice) {
        this.customerUnitPrice = BigDecimal.valueOf(customerUnitPrice).setScale(2, RoundingMode.HALF_UP);
    }

    public void setCustomerFreight(double customerFreight) {
        this.customerFreight = BigDecimal.valueOf(customerFreight).setScale(2, RoundingMode.HALF_UP);
    }

    public void setCustomerShelvingFee(double customerShelvingFee) {
        this.customerShelvingFee = BigDecimal.valueOf(customerShelvingFee).setScale(2, RoundingMode.HALF_UP);
    }

    public void setCustomerMiscellaneousFees(double customerMiscellaneousFees) {
        this.customerMiscellaneousFees = BigDecimal.valueOf(customerMiscellaneousFees).setScale(2, RoundingMode.HALF_UP);
    }

    public void setInsuranceFee(double insuranceFee) {
        this.insuranceFee = BigDecimal.valueOf(insuranceFee).setScale(2, RoundingMode.HALF_UP);
    }

    public void setCustomerInitialBillingTotal(double customerInitialBillingTotal) {
        this.customerInitialBillingTotal = BigDecimal.valueOf(customerInitialBillingTotal).setScale(2, RoundingMode.HALF_UP);
    }
}
