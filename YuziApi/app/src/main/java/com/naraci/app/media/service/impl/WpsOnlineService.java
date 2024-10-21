package com.naraci.app.media.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naraci.app.media.entity.request.CheckTemplateRequest;
import com.naraci.app.media.entity.request.DynamicRow;
import com.naraci.app.media.entity.request.QuotationRequest;
import com.naraci.app.media.entity.response.WpsAllDataResponse;
import com.naraci.core.aop.CustomException;
import com.spire.xls.FileFormat;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
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
//                    .addHeader("AirScript-Token", "48WgK4eZsSes7NdDkuEGHV") // 测试环境Token
                .addHeader("AirScript-Token", "6KB1OjQFfVPn4wGPZsiCE6") // 生产环境Token
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
            responseItem.setGoodsCostGet(item.get(28).asDouble(0.0));
            responseItem.setCustomerMiscellaneousFees(item.get(29).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setInsuranceFee(item.get(30).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setRemarks(item.get(31).asText(""));
            responseItem.setCustomerInitialBillingTotal(item.get(32).asDouble(0.0)); // 使用 Double 类型输入
            responseItem.setCustomerPaymentDate(item.get(33).asText(""));
            responseItem.setPayBody(item.get(34).asText("N/A"));

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

    public void pushWxRobot(CheckTemplateRequest obj) {
        String id = checkPdfFile(obj);
        // 创建 OkHttpClient 实例
        OkHttpClient client = new OkHttpClient();

        // 去掉引号
        id = id.replace("\"", "");

        String json = String.format("{\n" +
                "    \"msgtype\": \"file\",\n" +
                "    \"file\": {\n" +
                "        \"media_id\": \"%s\"\n" +
                "    }\n" +
                "}", id);

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
            log.info(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String checkPdfFile(CheckTemplateRequest object) {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 定义日期格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);

        // 创建写入表格数据列表
        List<String> results = new ArrayList<>();
        results.add(formattedDate);
        results.add(object.getCustomerCode());
        results.add(object.getWarehousingNumber());
        results.add("头程费用");
        results.add(String.valueOf(object.getSkuTotalCount()));
        results.add(String.valueOf(object.getOriginalWeightAfterWrapping()));
        results.add(String.valueOf(object.getCustomerUnitPrice()));
        results.add(String.valueOf(object.getCustomerFreight()));
        results.add(String.valueOf(object.getCustomerShelvingFee()));
        results.add("0.00");
        results.add(String.valueOf(object.getGoodCostGet()));
        results.add("0.00");
        results.add(String.valueOf(object.getInsuranceFee()));
        results.add(String.valueOf(object.getCustomerMiscellaneousFees()));
        results.add(String.valueOf(object.getCustomerInitialBillingTotal()));
        results.add(object.getRemarks());
        results.add(object.getPrincipal());

        // 输出 Excel 文件路径
        String outputPath = "yuziapi/file";
        File fileDirectory = new File(outputPath);
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
        }
        // Excel 模板路径
        String templatePath = "yuziapi/template/头程账单模板.xlsx";
        // 定义输出的文件名
        String outputFilePath = outputPath + "/头程费用账单-" + formattedDate + "-" + object.getWarehousingNumber() +
                "-费用合计-" + object.getCustomerInitialBillingTotal() + "元" + ".xlsx";
        String outputFilePdfPath = outputPath + "/头程费用账单-" + formattedDate + "-" + object.getWarehousingNumber() +
                "-费用合计-" + object.getCustomerInitialBillingTotal() + "元" + ".pdf";
        File outputFile = new File(outputFilePath);

        try (FileInputStream fileInputStream = new FileInputStream(templatePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            // 获取第一个工作表
            Sheet sheet = workbook.getSheetAt(0);
            int startRow = 7;  // 从第8行开始
            int startColumn = 0;  // 从第1列开始

            Row row = sheet.getRow(startRow);
            if (row == null) {
                row = sheet.createRow(startRow);
            }

            // 填充15个单元格
            for (String item : results) {
                Cell cell = row.getCell(startColumn);
                if (cell == null) {
                    cell = row.createCell(startColumn);
                }
                if (startColumn == 16) {
                    continue;
                }
                cell.setCellValue(item);
                startColumn++;
            }
            // 单独设置 11列12行
            // 下部合计
            Row sumRow = sheet.getRow(11);
            Cell sumMoney = sumRow.getCell(10);
            sumMoney.setCellValue(results.get(14));

            //设置销售
            Row peopleRow = sheet.getRow(11);
            Cell peopleCol = peopleRow.getCell(1);
            peopleCol.setCellValue(results.get(16));

            // 写入数据到输出文件
            try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                workbook.write(fileOutputStream);
            }
            Boolean isTrue = convertExcelToPdf(outputFilePath, outputFilePdfPath);
            if (!isTrue) {
                throw new CustomException("推送失败");
            }
            return mediaId(outputFilePdfPath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outputFile.delete();
            File pdfFile = new File(outputFilePdfPath);
            pdfFile.delete();
        }

        // 返回生成的文件对象
        return null;
    }

    public String mediaId(String filePath) throws IOException {
        // Webhook URL (含key)
        String url = "https://qyapi.weixin.qq.com/cgi-bin/webhook/upload_media?key=4387ab7d-e419-44a2-acc5-c54fe19b6d25&type=file";
        try {
            // 调用上传文件方法并打印结果
            String mediaId = requestMediaResponse(url, filePath);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(mediaId);
            log.info("Media ID: " + jsonNode.get("media_id"));
            return String.valueOf(jsonNode.get("media_id"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 获取media_id
    public static String requestMediaResponse(String url, String filePath) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // 创建文件对象
        File file = new File(filePath);

        // 创建文件的请求体
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);

        // 创建 multipart/form-data 的请求体
        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("media", file.getName(), fileBody)  // "media" 为表单项名称
                .build();

        // 构建 POST 请求
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        // 执行请求
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // 返回响应体
            return response.body().string();
        }
    }

    public static Boolean convertExcelToPdf(String excelFilePath, String pdfFilePath) {
        // 加载Excel文档.
        try {
            com.spire.xls.Workbook wb = new com.spire.xls.Workbook();
            wb.loadFromFile(excelFilePath);
            // 调用方法保存为PDF格式.
            wb.saveToFile(pdfFilePath, FileFormat.PDF);
            return true;
        }catch (Exception e) {
            throw new CustomException("导出失败!");
        }
    }

    public void quotation(QuotationRequest request) {
        log.info(request.toString());

// 模拟接收到的数据
        List<DynamicRow> collecList = request.getDynamicRows();
        int dynamicRowSize = collecList.size();  // 动态列数量

// 输出 Excel 文件路径
        String outputPath = "yuziapi/file";
        File fileDirectory = new File(outputPath);
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
        }

// Excel 模板路径
        String templatePath = "yuziapi/template/报价模板.xlsx";

        try (FileInputStream fileInputStream = new FileInputStream(templatePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            // 获取当前日期
            LocalDate currentDate = LocalDate.now();

            // 定义日期格式化器
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = currentDate.format(formatter);

            /** 默认只填写B列
             *  固定列 3-16行
             *  动态列 17-23行，动态列超过1个时向右偏移对应的列数
             */

            // 获取第一个工作表
            Sheet sheet = workbook.getSheetAt(0);
            // 设置日期
//            sheet.setrow
            // 创建一行（第0行）
            Row rowData = sheet.getRow(24);

            // 创建一个单元格（第0列）
            Cell cellData = rowData.getCell(3);

            // 设置单元格的值
            cellData.setCellValue("日期:"+ formattedDate);

            // 写入固定列数据到B列（第3-16行）
            String[] fixedColumnData = {
                    request.getCustomerCode(), request.getTrackingNumber(), request.getItemName(),
                    request.getDestination(), request.getProductCategory(), String.valueOf(request.getQuantity()),
                    String.valueOf(request.getWeight()), String.valueOf(request.getVolume()),
                    String.valueOf(request.getDensity()), String.valueOf(request.getValue()),
                    String.valueOf(request.getInsuranceFee()), String.valueOf(request.getPickupFee()),
                    String.valueOf(request.getShelvingUnitPrice()), String.valueOf(request.getShelvingFee())
            };

            int startRowForFixed = 2;  // 从第3行开始（Excel中的行从1开始，数组从0）
            int fixedColumn = 1;  // B列（索引从0开始）
            for (int i = 0; i < fixedColumnData.length; i++) {
                Row row = sheet.getRow(startRowForFixed + i);
                if (row == null) {
                    row = sheet.createRow(startRowForFixed + i);
                }
                Cell cell = row.getCell(fixedColumn);
                if (cell == null) {
                    cell = row.createCell(fixedColumn);
                }
                cell.setCellValue(fixedColumnData[i]);
            }

            // 写入动态列数据到相应位置，从第17行开始
            int startRowForDynamic = 16;  // 第17行（Excel中的行从1开始，数组从0）

            // 循环处理每个动态列的数据
            for (int dynamicIndex = 0; dynamicIndex < dynamicRowSize; ++dynamicIndex) {
                DynamicRow dynamicRow = collecList.get(dynamicIndex);
                int dynamicStartColumn = fixedColumn + dynamicIndex;  // 动态列的位置，从第一个动态列开始的相邻列（从C列开始）

                // 写入动态列数据（第17-23行）
                String[] dynamicColumnData = {
                        dynamicRow.getTransitTime(),
                        String.valueOf(dynamicRow.getFreightUnitPrice()),
                        String.valueOf(dynamicRow.getPackingFee()),
                        String.valueOf(dynamicRow.getPalletFee()),
                        String.valueOf(dynamicRow.getCrateFee()),
                        String.valueOf(dynamicRow.getTotalCost())
                };

                for (int rowIndex = 0; rowIndex < dynamicColumnData.length; rowIndex++) {
                    Row row = sheet.getRow(startRowForDynamic + rowIndex);
                    if (row == null) {
                        row = sheet.createRow(startRowForDynamic + rowIndex);
                    }
                    Cell cell = row.getCell(dynamicStartColumn); // 每个动态列开始填入对应的列
                    if (cell == null) {
                        cell = row.createCell(dynamicStartColumn);
                    }
                    cell.setCellValue(dynamicColumnData[rowIndex]);
                }
            }


            // 输出到文件
            String outputFilePath = outputPath + "/报价表格.xlsx";
            try (FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath)) {
                workbook.write(fileOutputStream);
                log.info("写入成功路径" + outputFilePath);
                excelExportPng();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void excelExportPng() throws IOException {
        // 加载Excel文件
        FileInputStream file = new FileInputStream("yuziapi/file/报价表格.xlsx");
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表

        // 设置图片大小，根据表格大小动态调整
        int imageWidth = sheet.getRow(0).getLastCellNum() * 100;  // 根据列数调整宽度
        int imageHeight = sheet.getLastRowNum() * 20;  // 根据行数调整高度

        // 创建BufferedImage
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);  // 背景设置为白色
        graphics.fillRect(0, 0, imageWidth, imageHeight); // 填充背景色

        // 设置字体
        Font font = new Font("Arial", Font.PLAIN, 12);
        graphics.setFont(font);
        graphics.setColor(Color.BLACK);  // 设置字体颜色为黑色

        // 遍历表格行和列，绘制每个单元格的内容
        for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                for (int colIndex = 0; colIndex < row.getLastCellNum(); colIndex++) {
                    Cell cell = row.getCell(colIndex);
                    if (cell != null) {
                        String cellValue = getCellValue(cell);
                        int x = colIndex * 100;  // 每列宽度为100像素
                        int y = rowIndex * 20 + 15;  // 每行高度为20像素，+15是为了调整文本的Y轴位置
                        graphics.drawString(cellValue, x, y);
                    }
                }
            }
        }

        // 将BufferedImage保存为文件
        ImageIO.write(image, "png", new File("output_image.png"));

        // 关闭资源
        graphics.dispose();
        workbook.close();
        file.close();
    }

    // 获取单元格的字符串内容
    private static String getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}

