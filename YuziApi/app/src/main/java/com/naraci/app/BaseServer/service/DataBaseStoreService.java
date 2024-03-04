package com.naraci.app.BaseServer.service;

import com.naraci.app.BaseServer.domain.ImageAcg;
import com.naraci.app.BaseServer.mapper.ImageAcgMapper;
import com.naraci.app.media.entity.request.SrcRequest;
import com.naraci.core.aop.CustomException;
import com.naraci.core.util.StringUtils;
import jakarta.annotation.Resource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author ShenZhaoYu
 * @date 2024/3/5
 */
@Service
public class DataBaseStoreService {

    private String localUrl = "https://www.ciyuanjie.cn";

    @Resource
    private ImageAcgMapper imageAcgMapper;

    public void imageAcg(SrcRequest request) throws IOException, InterruptedException {
        Document doc = Jsoup.parse(new URL(request.getUrl()), 30000);
        Elements tag = doc.getElementsByClass("page-numbers");
        Element e = tag.get(tag.size() - 2);
        String pageText = e.text();
        int pageSum = Integer.parseInt(StringUtils.RequestNumber(pageText));

        List<String> pageUrlList = new ArrayList<>();
        for (int i = 0; i <= pageSum; i++) {
            String s = request.getUrl() + "/page_" + i + ".html";
            pageUrlList.add(s);
        }
        LocalPageUrl(pageUrlList);
    }

    public void LocalPageUrl(List<String> urls) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10); // 创建一个固定大小的线程池
        for (String url : urls) {
            executorService.execute(() -> {
                try {
                    Document doc = Jsoup.parse(new URL(url), 30000);
                    Elements allLink = doc.select("#index_ajax_list");
                    Elements allA = allLink.select(".kzpost-data");
                    for (int i = 0; i <= allA.size() - 1; ++i) {
                        Element text = allA.get(i);
                        Elements a1 = text.select("a");
                        String value = a1.attr("href");
                        traverse(localUrl + value);
                    }
                } catch (Exception e) {
                    throw new CustomException("连接超时");
                }
            });
        }
        executorService.shutdown(); // 关闭线程池
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS); // 等待所有线程执行完毕
    }

    public void traverse(String url) {
        ExecutorService executorService = Executors.newFixedThreadPool(10); // 创建一个固定大小的线程池
        try {
            Document dc = Jsoup.parse(new URL(url), 30000);
            Elements imgs = dc.getElementsByTag("img");
            Elements targetClass = imgs.select(".aligncenter");

            List<String> imagesLinks = new ArrayList<>();
            for (Element aClass : targetClass) {
                String imageUrl = aClass.attr("src");
                imagesLinks.add(imageUrl);
            }

            Elements titleGet = dc.getElementsByTag("title");
            String title = titleGet.text();
            for (int i = 0; i < imagesLinks.size(); i++) {
                int finalI = i;
                executorService.execute(() -> {
                    ImageAcg imageAcg = new ImageAcg();
                    imageAcg.setName(title + '-' + finalI);
                    imageAcg.setUrl(imagesLinks.get(finalI));
                    imageAcgMapper.insert(imageAcg);
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown(); // 关闭线程池
        }
    }
}
