import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.naraci.core.util.StringUtils;
import com.naraci.core.util.UrlUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author ShenZhaoYu
 * @date 2024/3/2
 */

public class TestAc {

    @Test
    public void Test() throws Exception {
//        String url = "https://kyfw.12306.cn/otn/leftTicket/init?linktypeid=dc&fs=%E5%8C%97%E4%BA%AC%E5%8C%97,VAP&ts=%E5%A4%A9%E6%B4%A5%E8%A5%BF,TXP&date=2024-03-03&flag=N,N,Y";
//
//        UrlUtils.ignoreSSL();
//        Document dc = Jsoup.parse(new URL(url), 30000);
//        System.out.println(dc);
        String encodedString = "oI9dHT94AiN6skYv1vo64nh7AGR8zbJiQ1JSR6Ph5nVTt%2FEynm1Cme%2Fu7O6X24BAYKGy4%2BUeAzd7%0ARN%2BhLnu%2BqjrEWqe%2Bl33ecbH5Jqeg%2FVsWyKDOkMXLJHXvB2xWntYTFSfy40Ka9B0W%2Bh1P7bXP7bT7%0AhKPJodNpvWEVjM%2B7agxaEzyFU17SwUkNVMTOkl6N0j2XIhGEFQMcpuzZ%2Bizqd3f6VI4psEsooubX%0ADsgxbDRqfpbBbeVDQz%2FtRFbxznWgUU79%2FWfc%2FYnL%2BdUR5nJ38AG5zSlNy7NEdzaaCsctresT5kwU%0AbElqagzCGUCRTCi4xQkeR3earfrLkA1JzT%2B8rg%3D%3D|预订|24000C20410N|C2041|VNP|TJP|VNP|WWP|13:23|13:44|00:21|Y|T1Iaih2SR65Rc5evYa2fORNpMkFV%2BaQmiaGNOaeRYX35X7X8|20240303|3|P2|01|02|1|0|||||||||||有|11|10||90M0O0|9MO|1|0||9012200010M006200011O003850021|0|||||1|0#1#0#0#z#0#z||7|CHN,CHN|||N#N#|||202402181230|";

        try {
            String decodedString = URLDecoder.decode(encodedString, "GB18030");
            System.out.println(decodedString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void pImage() throws IOException, InterruptedException {
//        String url = "https://www.ciyuanjie.cn/pz/";
        String url = "https://www.ciyuanjie.cn/cosplay";

        // 由于子页面都来自于主url拼接地址 所有定义一下方便循环拼接
        String localUrl = "https://www.ciyuanjie.cn";

        Document doc = Jsoup.parse(new URL(url), 3000);
        // 获取总页数
        Elements tag = doc.getElementsByClass("page-numbers");
        Element e = tag.get(tag.size() -2);
        // 提取文本内容
        String pageText = e.text();
        int pageSum = Integer.parseInt(StringUtils.RequestNumber(pageText));

        // 创建一个存储待抓取链接页面的列表
        List<String> links = new ArrayList<>();
        //  获取所有的链接
        Elements allLink = doc.select("#index_ajax_list");
        Elements allA = allLink.select(".kzpost-data");
        for (int i = 0; i<= allA.size()-1; ++i) {
            Element text = allA.get(i);
            Elements a1 = text.select("a");
            String value = a1.attr("href");
            links.add(localUrl+value);
        }

        //
        String targetUrl = "https://www.ciyuanjie.cn/78083.html";

        //解析这个网页
        Document dc = Jsoup.parse(new URL(targetUrl), 3000);
        // 选择全部的img标签
        Elements imgs = dc.getElementsByTag("img");
        // 选择要的class
        Elements targetClass = imgs.select(".aligncenter");

        // 创建一个要下载图片链接的数组
        List<String> imagesLinks = new ArrayList<>();
        for (Element aClass : targetClass) {
            // 获得sec这个属性
            String imageUrl = aClass.attr("src");
            imagesLinks.add(imageUrl);
        }

        // 获取 title 作为文件夹名称
        Elements titleGet = dc.getElementsByTag("title");
        String title = titleGet.text();
        // 下载
        imageDownload(imagesLinks, title);

    }

    public void imageDownload(List<String> imageLinks, String title) throws IOException {

        //创建文件夹
        // 定义一个路径 这里是springboot 的路径
        String filePath = "classpath:/Images";

        File newFile = new File(filePath+title);
        // 判断是否存在
        if (!newFile.exists()) {
            System.out.println("文件夹创建失败");
        }else {
            System.out.println("文件夹创建成功"+newFile);
            return;
        }
        int i = 1;
        for(String url : imageLinks) {
            String redirected = UrlUtils.getRedirectedURL(url);
            URL tarGet = new URL(redirected);
            System.out.println(tarGet);
            // 创建一个链接
            HttpURLConnection httpURLConnection = (HttpURLConnection) tarGet.openConnection();
            // 创建一个文件输入流来获取存入资源的连接
            InputStream inputStream = httpURLConnection.getInputStream();

            // 新建一个文件 来保存流的内容
            try {
                FileOutputStream file = new FileOutputStream(i + ".jpg");

                // 创建一个字节数组来当 缓冲区
                byte[] buffer = new byte[10];
                int len;

                // 读取数据
                while ((len = inputStream.read(buffer)) != -1) {
                    file.write(buffer, 0, len);
                }
                inputStream.close();
                file.close();
                System.out.println("保存成功");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                httpURLConnection.disconnect();
            }
            i++;
            inputStream.close();
        }

    }
}
