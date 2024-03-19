import com.naraci.app.WechatRobot.beans.BaseMsg;
import com.naraci.app.WechatRobot.service.impl.WechatModelService;
import com.naraci.app.media.entity.response.WeiBoHotResponse;
import com.naraci.app.media.service.impl.MediaService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author ShenZhaoYu
 * @date 2024/3/16
 */
@Slf4j
@Component
public class wechatlogin {

    @Test
    public void main () throws IOException {
        MediaService mediaService = new MediaService();
        List<WeiBoHotResponse> weibo = mediaService.weibo();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < weibo.size(); i++ ) {
           WeiBoHotResponse w = weibo.get(i);
           text.append(w.getRank()).append(".").append(w.getTitle()).append("\n 类型:").append(w.getType())
                   .append("\n 热度:").append(w.getHot()).append("\n 时间:").append(w.getTime()).append("\n \n");
        }
        System.out.println(text);
    }

    public String returnCode() {
        String wxUrl = "https://login.weixin.qq.com/l/";
        String url = "https://login.wx.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https%3A%2F%2Fwx.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage";
        return "url";
    }

    @Test
    public void douyin() throws Exception {
        BaseMsg baseMsg = new BaseMsg();
        baseMsg.setText("抖音 4.15 复制打开抖音，看看【朝慕的作品】冷知识：输入“好崩溃”抖音会安慰你  https://v.douyin.com/iF9Yauan/ K@w.sr tRk:/ 06/06 ");
        WechatModelService simpleDemo = new WechatModelService();
        String s = simpleDemo.textMsgHandle(baseMsg);
        log.info(s);
    }
}
