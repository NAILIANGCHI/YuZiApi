package com.naraci.app.media.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.naraci.app.media.mapper.DouyinConfigMapper;
import com.naraci.app.media.entity.request.SrcRequest;
import com.naraci.app.media.entity.response.DouyinImageResponse;
import com.naraci.app.media.entity.response.DouyinVideoResponse;
import com.naraci.app.media.pojo.DouyinConfig;
import com.naraci.core.aop.CustomException;
import com.naraci.core.util.UrlUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.cookie.BasicClientCookie;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;


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

        String mxUrl = UrlUtils.urlShareIsTrue(url.getUrl());

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

    public DouyinImageResponse douyinImage(SrcRequest url) throws Exception {
        int ss = 0;
        if (ss == 0) {
            throw new CustomException("维护中。。。");
        }
        // 判断分享链接是否合法
        String urlOk = UrlUtils.urlShareIsTrue(url.getUrl());

        DouyinImageResponse rp = new DouyinImageResponse();
        String newUrl =  UrlUtils.getDouYinRedirectUrl(urlOk);
        // 将图集id取出来
        String id = UrlUtils.douYinExtractImageId(newUrl);

        DouyinConfig douyinConfig = douyinConfigMapper.selectOne(
                Wrappers.lambdaQuery(DouyinConfig.class)
        );

        String cookieValue = douyinConfig.getCookie();
        String userAgent = douyinConfig.getUserAgent();

        CookieStore cookieStore = new BasicCookieStore();

        cookieStore.addCookie(new BasicClientCookie("Cookie", cookieValue));

        CloseableHttpClient httpClient = HttpClients.custom()
                .build();

        try {
            String uUrl = "https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?reflow_source=reflow_page&item_ids="+id+"&a_bogus=DyMOgOZJMsR1Xj3Cdwkz9Htm54W0YW40gZEzYr%2FdMtL9";
            URL spUrl = new URL(uUrl);

            HttpURLConnection connection = (HttpURLConnection) spUrl.openConnection();

            connection.setRequestMethod("GET");

            //设置请求头
            connection.setRequestProperty("User-Agent", userAgent);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Cookie", cookieValue);

            //获取响应代码
            int responseCode = connection.getResponseCode();
            if (responseCode ==HttpURLConnection.HTTP_OK) {
                // 创建BufferedReader 对象读取
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                // 读取响应的内容
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // 解析json 数据
                JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();

                // 获取 json里的数据
                JsonArray jsonMain = jsonObject.getAsJsonArray("item_list");
                JsonArray jsonArrayZero = jsonMain.getAsJsonArray();
                JsonElement jsonArrayMain = jsonArrayZero.get(0);
                JsonObject jsMain = jsonArrayMain.getAsJsonObject();
                // 设置标题
                JsonObject jsonArray = jsMain.get("author").getAsJsonObject();
                String title = jsonArray.getAsJsonObject().get("signature").getAsString();
                // 设置背景音乐
                JsonObject jsonMp3 = jsMain.get("video").getAsJsonObject();
                JsonObject ArraysMp3 = jsonMp3.get("play_addr").getAsJsonObject();
                String mp3 = ArraysMp3.get("uri").getAsString();

                JsonArray jsonUrlList = jsMain.get("images").getAsJsonArray();
                JsonArray jsonUrlArrays = jsonUrlList.getAsJsonArray();
                List<String> imageList = new ArrayList<>();
                for (int i=0; i < jsonUrlArrays.size(); i++) {
                    JsonElement jsIndex = jsonUrlArrays.get(i);
                    JsonObject urlList = jsIndex.getAsJsonObject();
                    if (urlList.size() > 0) {
                        JsonArray urlImages = urlList.get("url_list").getAsJsonArray();
                        String urlImage = urlImages.get(0).getAsString();
                        imageList.add(urlImage);
                        System.out.println(urlImage);
                    }
                 }
                rp.setTitle(title);
                rp.setMp3(mp3);
                rp.setImages(imageList);
                return rp;
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new CustomException("解析失败，请联系管理员检修！");
        }
        return null;
    }
}
