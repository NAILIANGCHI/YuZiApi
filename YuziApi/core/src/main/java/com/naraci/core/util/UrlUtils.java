package com.naraci.core.util;

import com.naraci.core.aop.CustomException;
import lombok.extern.slf4j.Slf4j;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ShenZhaoYu
 * @date 2024/2/22
 *url 工具
 */
@Slf4j
public class UrlUtils {

    /**
     * 获取重定向地址
     * @param path
     * @return
     * @throws Exception
     */
    public static String getDouYinRedirectUrl(String path) throws Exception {

        // 定义正则表达式
        String regex = "https?://(?:www\\.|v\\.)douyin\\.com/";

        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex);

        // 创建 Matcher 对象
        Matcher matcher = pattern.matcher(path);

        // 查找匹配项
        if (matcher.find()) {
            // 获取匹配到的结果
            String match = matcher.group();


            // 判断是 v 还是 www
            if (match.contains("v")) {
                HttpURLConnection conn = (HttpURLConnection) new URL(path)
                        .openConnection();
                conn.setInstanceFollowRedirects(false);
                conn.setConnectTimeout(5000);
                return conn.getHeaderField("Location");

            } else if (match.contains("www")) {
                return path;
            } else {
                throw new CustomException("链接不合法！");
            }
        }
        return null;
    }

    /**
     * 抖音id获取
     * @param url
     * @return
     */
    public static String douYinExtractVideoId(String url) {
        if (url == null) {
            throw new CustomException("链接不合法！");
        }
        Pattern compiledPattern = Pattern.compile("/(\\d+)/?");
        Matcher matchers = compiledPattern.matcher(url);
        if (matchers.find()) {
            return matchers.group(1);
        } else {
            // 编译正则表达式
            Pattern pattern = Pattern.compile("modal_id=(\\d+)");
            // 创建 Matcher 对象
            Matcher matcher = pattern.matcher(url);
            // 查找匹配项
            if (matcher.find()) {
                // 获取匹配到的id
                return matcher.group(1);

            } else {
                throw new CustomException("链接不合法");
            }
        }
    }

    /**
     * 抖音图集id获取
     * @param url
     * @return
     */
    public static String douYinExtractImageId(String url) {
        if (url == null) {
            throw new CustomException("链接不合法！");
        }
        Pattern pattern = Pattern.compile("(?<=\\/)\\d{19}(?=\\/|$)");
        Matcher matcher = pattern.matcher(url);
        // 查找匹配的ID并返回
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            log.debug(matcher.toString());
            throw new CustomException("链接不合法");
        }

    }

    /**
     * 判断分享链接是否合法
     * @param url
     * @return
     */
    public static String urlShareIsTrue(String url) {
        // 匹配链接的简单正则表达式
        String regex = "https?://\\S+";

        // 创建 Pattern 对象
        Pattern pattern = Pattern.compile(regex);

        // 创建 Matcher 对象
        Matcher matcher = pattern.matcher(url);

        String mxUrl = "";

        // 查找匹配的链接
        if (matcher.find()) {
            return mxUrl = matcher.group();
        }else {
            throw new CustomException("输入的链接不合法");
        }
    }
}
