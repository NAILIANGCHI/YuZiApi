package com.naraci.app.media.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naraci.app.media.entity.response.WpsAllDataResponse;
import com.naraci.core.aop.CustomException;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WpsOnlineService {
    // 生产环境
    private final String AllPageDataUrl = "https://www.kdocs.cn/api/v3/ide/file/ctb8HSfxYfih/script/V2-73LnHV8Jjef1ezd8sZMUvw/sync_task";
    // 测试环境
    private final String AllTestPageDataUrl = "https://www.kdocs.cn/api/v3/ide/file/cbA5u9W7wiyE/script/V2-79oPH3I0t8hewTsA0sSb7X/sync_task";
    // 使用 Jackson 进行 JSON 解析
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 构建接口请求
    public Response wpsRequest(String url, String requestBodyContent) throws IOException {
        try{
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, requestBodyContent);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
//                    .addHeader("AirScript-Token", "6fgzLVfeHde8oa1aZwuNEi") // 测试环境Token
                .addHeader("AirScript-Token", "6EVBnuLPhrivypRvAw48mH") // 生产环境Token
                    .build();
            return client.newCall(request).execute();
        }catch (SocketTimeoutException e) {
            throw new CustomException("请求频繁!让我缓缓....");
        }
    }

    // 修改后的方法，支持分页参数
    public WpsPageDataResponse requestAllData(int page, int pageSize) throws IOException {
        // 构建请求体，包含分页参数
        String requestBodyContent = String.format(
//                "{\"Context\":{\"argv\":{\"name\":\"zhaoyu\", \"page\": %d, \"pageSize\": %d},\"sheet_name\":\"头程测试\"}}",
                "{\"Context\":{\"argv\":{\"name\":\"zhaoyu\", \"page\": %d, \"pageSize\": %d},\"sheet_name\":\"头程发货明细\"}}",
                page, pageSize
        );

        // 发出请求
        Response response = wpsRequest(AllPageDataUrl, requestBodyContent);
        if (!response.isSuccessful()) {
            throw new IOException("错误code " + response);
        }

        // 获取响应体
        assert response.body() != null;
        String responseBody = response.body().string();

        // 解析 JSON 响应为 JsonNode
        JsonNode rootNode = objectMapper.readTree(responseBody);
        JsonNode pages = rootNode.path("data").path("result");
        JsonNode logsNode = rootNode.path("data").path("result").path("data");

        // 转换为 WpsAllDataResponse 列表
        List<WpsAllDataResponse> allDataResponses = new ArrayList<>();
        for (JsonNode item : logsNode) {
            WpsAllDataResponse responseItem = new WpsAllDataResponse();

            // 填充数据
            responseItem.setSerialNumber(item.get(0).asText("N/A"));
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
            responseItem.setPrincipal(item.get(14).asText("N/A"));
            responseItem.setProductName(item.get(15).asText(""));
            responseItem.setCategory(item.get(16).asText(""));
            responseItem.setValue(item.get(17).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setSkuTotalCount(item.get(18).asInt(0));
            responseItem.setBoxCount(item.get(19).asInt(0));
            responseItem.setCustomerPackagingTotalWeight(item.get(20).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setCustomerPackagingTotalVolume(item.get(21).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setOriginalWeightAfterWrapping(item.get(22).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setOriginalVolumeAfterWrapping(item.get(23).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setDensityAfterWrapping(item.get(24).asInt()); // 假设密度是整数
            responseItem.setCustomerUnitPrice(item.get(25).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setCustomerFreight(item.get(26).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setCustomerShelvingFee(item.get(27).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setCustomerMiscellaneousFees(item.get(28).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setInsuranceFee(item.get(29).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setRemarks(item.get(30).asText(""));
            responseItem.setCustomerInitialBillingTotal(item.get(31).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setCustomerPaymentDate(item.get(32).asText(""));
            responseItem.setPayBody(item.get(33).asText("N/A"));

            allDataResponses.add(responseItem);
        }

//        System.out.println(pages.findValue("totalPages"));
//        System.out.println(pages.findValue("currentPage"));
//        System.out.println(pages.findValue("pageSize"));
//        System.out.println(pages.findValue("totalRecords"));

        WpsPageDataResponse wpsPageDataResponse = new WpsPageDataResponse();
        wpsPageDataResponse.setStartIndex(pages.findValue("currentPage").intValue());
        wpsPageDataResponse.setEndIndex(pages.findValue("pageSize").intValue());
        wpsPageDataResponse.setTotalRecords(pages.findValue("totalRecords").intValue());
        wpsPageDataResponse.setTotalPages(pages.findValue("totalPages").intValue());
        wpsPageDataResponse.setPageData(allDataResponses);

        return wpsPageDataResponse;

/*          const totalRecords =rowData.length; // 总记录数
            const totalPages =Math.ceil(totalRecords /pageSize); // 总页数
            const startIndex =(page -1)*pageSize; // 起始索引
            const endIndex =startIndex +pageSize; // 结束索引
            const pagedData =rowData.slice(startIndex,endIndex); // 分页数据*/
    }
}
