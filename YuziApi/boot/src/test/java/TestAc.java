//import com.naraci.core.util.StringUtils;
//import com.naraci.core.util.UrlUtils;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.junit.jupiter.api.Test;
//
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///**
// * @author ShenZhaoYu
// * @date 2024/3/2
// */
//
//public class TestAc {
//
//    // 由于子页面都来自于主url拼接地址 所有定义一下方便循环拼接
//    private String localUrl = "https://www.ciyuanjie.cn";
//    private String jxLocalUrl = "https://www.ciyuanjie.cn/cosplay";
//
//    @Test
//    public void Thread() throws IOException {
//        TestAc testAc = new TestAc();
//        testAc.pImage();
//    }
//
//    /**
//     * 获取页面的所有选项目录链接
//     */
//    public void pImage() throws IOException {
//        String url = jxLocalUrl();
//
//        Document doc = Jsoup.parse(new URL(url), 30000);
//        // 获取总页数
//        Elements tag = doc.getElementsByClass("page-numbers");
//        Element e = tag.get(tag.size() - 2);
//        // 提取文本内容
//        String pageText = e.text();
//        int pageSum = Integer.parseInt(StringUtils.RequestNumber(pageText));
//
//        // 存储 所页面的url
//        List<String> pageUrlList = new ArrayList<>();
//        // 循环遍历 所有页
//        for (int i = 0; i <= pageSum; i++) {
//            String s = url + "/page_" + i + ".html";
//            pageUrlList.add(s);
//        }
//        LocalPageUrl(pageUrlList);
//    }
//
//    public void jxLocalUrl(String jxLocalUrl) {
//        this.jxLocalUrl = jxLocalUrl;
//    }
//
//    public String jxLocalUrl() {
//        return this.jxLocalUrl;
//    }
//
//    /**
//     * 根据页数来遍历图集资源地址
//     * @param urls
//     */
//    public void LocalPageUrl(List<String> urls) {
//        for (String url : urls) {
//            Document doc;
//            try {
//                doc = Jsoup.parse(new URL(url), 30000);
//                // 创建一个存储待抓取链接页面的列表
//                List<String> links = new ArrayList<>();
//                //  获取所有的链接
//                Elements allLink = doc.select("#index_ajax_list");
//                Elements allA = allLink.select(".kzpost-data");
//                for (int i = 0; i<= allA.size()-1; ++i) {
//                    Element text = allA.get(i);
//                    Elements a1 = text.select("a");
//                    String value = a1.attr("href");
//                    links.add(localUrl+value);
//                }
//                // 创建一个固定大小的线程池
//                ExecutorService executor = Executors.newFixedThreadPool(16);
//                for (String atlas : links) {
//                    executor.execute(new ImageDownloadThread(atlas));
//                }
//                executor.shutdown();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
//
//class ImageDownloadThread implements Runnable {
//    private String url;
//
//    public ImageDownloadThread(String url) {
//        this.url = url;
//    }
//
//    @Override
//    public void run() {
//        try {
//            traverse(url);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void traverse(String url) throws IOException {
//        Document dc = Jsoup.parse(new URL(url), 30000);
//        Elements imgs = dc.getElementsByTag("img");
//        Elements targetClass = imgs.select(".aligncenter");
//
//        List<String> imagesLinks = new ArrayList<>();
//        for (Element aClass : targetClass) {
//            String imageUrl = aClass.attr("src");
//            imagesLinks.add(imageUrl);
//        }
//
//        Elements titleGet = dc.getElementsByTag("title");
//        String title = titleGet.text();
//        imageDownload(imagesLinks, title);
//    }
//
//    public void imageDownload(List<String> imageLinks, String title) throws IOException {
//        String filePath = "D:\\Zhaoyu\\zhaoyuCode\\YuZiApi\\YuziApi\\boot\\src\\main\\resources\\Images";
//        File newFile = new File(filePath, title);
//        if (!newFile.mkdirs()) {
//            System.out.println("文件夹创建失败,当前文件夹名"+ title);
//            filePath = "D:\\Zhaoyu\\zhaoyuCode\\YuZiApi\\YuziApi\\boot\\src\\main\\resources\\Images\\创建失败的目录存放处";
//            newFile = new File(filePath);
//        } else {
//            System.out.println("文件夹创建成功" + newFile);
//        }
//        for(String url : imageLinks) {
//            String redirected = UrlUtils.getRedirectedURL(url);
//            URL tarGet = new URL(redirected);
//            HttpURLConnection httpURLConnection = (HttpURLConnection) tarGet.openConnection();
//            InputStream inputStream = httpURLConnection.getInputStream();
//            try {
//                String path = url.replaceAll("^https?://[^/]+/", "");
//                String fileName = path.replaceAll(".*/(.*?)$", "$1");
//                fileName = fileName.replaceAll("[\\\\/:*?\"<>|]", "_");
//                FileOutputStream file = new FileOutputStream(newFile.getAbsolutePath() + File.separator + fileName);
//                byte[] buffer = new byte[1024 * 2];
//                int len;
//                while ((len = inputStream.read(buffer)) != -1) {
//                    file.write(buffer, 0, len);
//                }
//                inputStream.close();
//                file.close();
//                System.out.println("保存成功");
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                httpURLConnection.disconnect();
//            }
//        }
//
//    }
//}
