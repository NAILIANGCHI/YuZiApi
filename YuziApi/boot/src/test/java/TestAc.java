import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;

/**
 * @author ShenZhaoYu
 * @date 2024/3/2
 */

public class TestAc {

    @Test
    public void Test() throws IOException {
        String url = "https://www.douyin.com/note/7320620221402795274";

        Document dc = Jsoup.parse(new URL(url), 30000);

//        System.out.println(element);
    }
}
