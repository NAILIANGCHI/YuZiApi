package com.naraci.app.media.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naraci.app.media.entity.response.WpsAllDataResponse;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WpsOnlineService {

    // 使用 Jackson 进行 JSON 解析
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<WpsAllDataResponse> requestAllData() throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"Context\":{\"argv\":{},\"sheet_name\":\"表名\",\"range\":\"A2:AF\"}}");
        Request request = new Request.Builder()
                .url("https://www.kdocs.cn/api/v3/ide/file/cbA5u9W7wiyE/script/V2-79oPH3I0t8hewTsA0sSb7X/sync_task") // 测试环境
//                .url("https://www.kdocs.cn/api/v3/ide/file/ctoGgbdWW4kZ/script/V2-1WC2LNDfRGOk8VtqmTBDrY/sync_task") // 生成环境
                .post(body)
                .addHeader("Content-Type", "application/json")
//                .addHeader("AirScript-Token", "27HJzGLSfGf77HU7AyjFia")// 生成环境token
                .addHeader("AirScript-Token", "6oawqWr5mmCidkgZK1JLKN")// 测试环境
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        // 获取响应体
        assert response.body() != null;
        String responseBody = response.body().string();

        // 解析 JSON 响应为 JsonNode
        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode logsNode = rootNode.path("data").path("result");
        System.out.println(rootNode);


        // 转换为 WpsAllDataResponse 列表
        List<WpsAllDataResponse> allDataResponses = new ArrayList<>();
        for (JsonNode item : logsNode) {
            WpsAllDataResponse responseItem = new WpsAllDataResponse();

            responseItem.setSerialNumber(item.get(0).asInt(0));
            responseItem.setSellerShipmentDate(item.get(1).asText(""));
            responseItem.setCustomerDeliveryTrackingNumber(item.get(2).asText(""));
            responseItem.setCarrierTrackingNumber(item.get(3).asText(""));
            responseItem.setTransferWarehouseInDate(item.get(4).asText(""));
            responseItem.setTransferDispatchDate(item.get(5).asText(""));
            responseItem.setMoscowPickupDate(item.get(6).asText(""));
            responseItem.setOverseasWarehouseInDate(item.get(7).asText(""));
            responseItem.setOrderStatus(item.get(8).asText(""));
            responseItem.setCustomerCode(item.get(9).asText(""));
            responseItem.setCustomerType(item.get(10).asText(""));
            responseItem.setCustomerName(item.get(11).asText(""));
            responseItem.setLogisticsMode(item.get(12).asText(""));
            responseItem.setWarehousingNumber(item.get(13).asText(""));
            responseItem.setProductName(item.get(14).asText(""));
            responseItem.setCategory(item.get(15).asText(""));
            responseItem.setValue(item.get(16).asDouble(0.0));  // 使用 Double 类型输入
            responseItem.setSkuTotalCount(item.get(17).asInt(0));
            responseItem.setBoxCount(item.get(18).asInt(0));
            responseItem.setCustomerPackagingTotalWeight(item.get(19).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setCustomerPackagingTotalVolume(item.get(20).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setOriginalWeightAfterWrapping(item.get(21).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setOriginalVolumeAfterWrapping(item.get(22).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setDensityAfterWrapping(item.get(23).asInt()); // 假设密度是整数
            responseItem.setCustomerUnitPrice(item.get(24).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setCustomerFreight(item.get(25).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setCustomerShelvingFee(item.get(26).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setCustomerMiscellaneousFees(item.get(27).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setInsuranceFee(item.get(28).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setRemarks(item.get(29).asText(""));
            responseItem.setCustomerInitialBillingTotal(item.get(30).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setCustomerPaymentDate(item.get(31).asText(""));

            allDataResponses.add(responseItem);
        }

        return allDataResponses;
    }
}
