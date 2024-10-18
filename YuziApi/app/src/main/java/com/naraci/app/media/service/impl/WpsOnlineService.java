package com.naraci.app.media.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naraci.app.media.entity.response.WpsAllDataResponse;
import com.naraci.core.aop.CustomException;
import okhttp3.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
                    .addHeader("AirScript-Token", "48WgK4eZsSes7NdDkuEGHV") // 测试环境Token
//                .addHeader("AirScript-Token", "6KB1OjQFfVPn4wGPZsiCE6") // 生产环境Token
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
                "{\"Context\":{\"argv\":{\"name\":\"zhaoyu\", \"page\": %d, \"pageSize\": %d},\"sheet_name\":\"头程测试\"}}",
//                "{\"Context\":{\"argv\":{\"name\":\"zhaoyu\", \"page\": %d, \"pageSize\": %d},\"sheet_name\":\"头程发货明细\"}}",
                page, pageSize
        );

        // 发出请求
        Response response = wpsRequest(AllTestPageDataUrl, requestBodyContent);
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

    public void pushWxRobot(String text) {
        // 创建 OkHttpClient 实例
        OkHttpClient client = new OkHttpClient();

        // 创建请求体
        String json = String.format("{\n" +
                "   \"msgtype\": \"text\",\n" +
                "   \"text\": {\n" +
                "       \"content\": \"%s\"\n" +
                "   }\n" +
                "}", text);

        // 创建 RequestBody
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, json);

        // 构建请求
        Request request = new Request.Builder()
                .url("https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=4387ab7d-e419-44a2-acc5-c54fe19b6d25")
                .post(body)
                .build();

        // 发送请求并处理响应
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // 输出响应内容
            assert response.body() != null;
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pushText(String text) {
        // Excel 模板路径
        String templatePath = "src/main/resources/Files";
        // 输出 Excel 文件路径
        String outputPath = "src/main/resources/filled_template.xlsx";
        try (FileInputStream fileInputStream = new FileInputStream(templatePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            // 获取第一个工作表
            Sheet sheet = workbook.getSheetAt(0);

            // 假设要填充第2行，第2列的单元格
            Row row = sheet.getRow(7); // 第2行（索引从0开始）
            if (row == null) {
                row = sheet.createRow(7); // 如果行不存在，创建新行
            }

            // 填充数据到第2列的单元格
            Cell cell = row.getCell(7); // 第2列
            if (cell == null) {
                cell = row.createCell(7); // 如果单元格不存在，创建新单元格
            }
            cell.setCellValue("新的数据");

            // 继续填充其他单元格...

            // 保存修改后的Excel
            try (FileOutputStream fileOut = new FileOutputStream(outputPath)) {
                workbook.write(fileOut);
                System.out.println("模板填充完成并保存为：" + outputPath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}