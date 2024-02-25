package com.naraci.app.media.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.naraci.app.mapper.DouyinConfigMapper;
import com.naraci.app.media.entity.request.SrcRequest;
import com.naraci.app.media.entity.response.DouyinImageResponse;
import com.naraci.app.media.entity.response.DouyinVideoResponse;
import com.naraci.app.pojo.DouyinConfig;
import com.naraci.core.aop.CustomException;
import com.naraci.core.util.UrlUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author ShenZhaoYu
 * @date 2024/2/20
 */
@Service
@Slf4j
public class MediaService {
    @Resource
    private DouyinConfigMapper douyinConfigMapper;

    public DouyinVideoResponse douyinVideo(SrcRequest url) throws Exception {

        // 匹配链接的简单正则表达式
        String regex = "https?://\\S+";

        // 创建 Pattern 对象
        Pattern pattern = Pattern.compile(regex);

        // 创建 Matcher 对象
        Matcher matcher = pattern.matcher(url.getUrl());

        String mxUrl = "";

        // 查找匹配的链接
        if (matcher.find()) {
             mxUrl = matcher.group();
        }else {
            throw new CustomException("输入的链接不合法");
        }

        DouyinVideoResponse rp = new DouyinVideoResponse();

       String newUrl =  UrlUtils.getDouYinRedirectUrl(mxUrl);

        DouyinConfig douyinConfig = douyinConfigMapper.selectOne(
                Wrappers.lambdaQuery(DouyinConfig.class)
        );

        String cookieValue = douyinConfig.getCookie();
        String userAgent = douyinConfig.getUserAgent();

        String id = UrlUtils.douYinExtractVideoId(newUrl);

        CookieStore cookieStore = new BasicCookieStore();

        cookieStore.addCookie(new BasicClientCookie("Cookie", cookieValue));

        CloseableHttpClient httpClient = HttpClients.custom()
                .build();

        try {
            URL spUrl = new URL("https://www.douyin.com/aweme/v1/web/aweme/detail/?device_platform=webapp&aid=6383&channel=channel_pc_web&aweme_id=" + id);

            // 创建请求并设置 URI
            HttpURLConnection connection = (HttpURLConnection) spUrl.openConnection();
            connection.setRequestMethod("GET");
            // 设置请求头
            connection.setRequestProperty("User-Agent", userAgent);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Cookie", cookieValue);

            // 获取响应代码
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 如果响应成功
                // 创建 BufferedReader 对象读取响应
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                // 读取响应内容
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // 解析 JSON 数据
                JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();

                // 获取 "play_addr" 中的 JsonObject
                JsonObject jsonMain = jsonObject.getAsJsonObject("aweme_detail");
                JsonObject jsonMusic = jsonMain.getAsJsonObject("music");
                JsonObject jsonMp3 = jsonMusic.getAsJsonObject("play_url");
                JsonObject jsonVideo = jsonMain.getAsJsonObject("video");
                JsonObject playAddr = jsonVideo.getAsJsonObject("play_addr");
                JsonArray urlList = playAddr.getAsJsonArray("url_list");

                // 提取所需的数据
                String title = jsonMain.get("preview_title").getAsString();
                String mp3 = jsonMp3.get("uri").getAsString();
                String mp4 = urlList.get(2).getAsString();

                rp.setTitle(title);
                rp.setMp3(mp3);
                rp.setMp4(mp4);

            }

        } catch (ClassCastException e) {
            throw new CustomException("解析失败，输入的链接可能不合法");
        }finally {
            // 关闭连接
            httpClient.close();
        }
        return rp;
    }

    public DouyinImageResponse douyinImage(SrcRequest url) {
        // 匹配链接的简单正则表达式
        String regex = "https?://\\S+";

        // 创建 Pattern 对象
        Pattern pattern = Pattern.compile(regex);

        // 创建 Matcher 对象
        Matcher matcher = pattern.matcher(url.getUrl());

        String mxUrl = "";

        // 查找匹配的链接
        if (matcher.find()) {
            mxUrl = matcher.group();
        }else {
            throw new CustomException("输入的链接不合法");
        }

        DouyinVideoResponse rp = new DouyinVideoResponse();

        return null;

    }
}
