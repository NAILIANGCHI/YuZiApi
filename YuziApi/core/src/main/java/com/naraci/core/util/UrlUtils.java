package com.naraci.core.util;

import com.naraci.core.aop.CustomException;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
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
                ignoreSSL();
                HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
                conn.setInstanceFollowRedirects(false);
                conn.setConnectTimeout(50000);
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP
                        || responseCode == HttpURLConnection.HTTP_MOVED_PERM
                        || responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
                    String redirectUrl = conn.getHeaderField("Location");
                    System.out.println("Redirected to: " + redirectUrl);
                    return redirectUrl;
                } else {
                    // 如果没有重定向，则返回原始URL
                    System.out.println("No redirection, returning original URL: " + path);
                    throw  new CustomException("解析异常，请联系管理员");
                }
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

    /**
     * 忽略ssl
     * @throws Exception
     */

    public static void ignoreSSL() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }

    /**
     * url 重定向
     * @param originalUrl
     * @return
     */
    public static String getRedirectedURL(String originalUrl) {
        try {
            URL url = new URL(originalUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(false);
            int responseCode = connection.getResponseCode();
            if (responseCode >= 300 && responseCode < 400) {
                String redirectedUrl = connection.getHeaderField("Location");
                if (redirectedUrl != null) {
                    // 确保重定向后的URL以"https://"开头
                    if (!redirectedUrl.startsWith("https://")) {
                        // 如果不是以"https://"开头，则替换为"https://"
                        redirectedUrl = "https://" + redirectedUrl.substring(redirectedUrl.indexOf("://") + 3);
                    }
                    return redirectedUrl;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return originalUrl; // 如果没有重定向，则返回原始URL
    }

    public static boolean is404(String url) throws IOException {
        URL newUrl = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) newUrl.openConnection();
        int codeState = httpURLConnection.getResponseCode();

        return codeState == HttpURLConnection.HTTP_NOT_FOUND;
    }
}
