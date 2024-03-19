package com.naraci.app.WechatRobot.controller;

import com.naraci.app.WechatRobot.Wechat;
import com.naraci.app.WechatRobot.face.IMsgHandlerFace;
import com.naraci.app.WechatRobot.service.impl.SimpleDemo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

import static com.naraci.app.WechatRobot.utils.FolderUtils.folderMk;

/**
 * @author ShenZhaoYu
 * @date 2024/3/18
 */
@RestController
@Tag(name = "微信机器人")
@RequestMapping("/robot")
public class WeChatBotStartController {

    @Resource
    @Autowired
    private IMsgHandlerFace simpleDemo = new SimpleDemo();

    @Operation(summary = "启动机器人")
    @PostMapping("/start")
    public void startRobot() {
        String qrPath = "D:\\Zhaoyu\\Chtgpt\\itchat4j-uos\\src\\main\\resources"; // 保存登陆二维码图片的路径，这里需要在本地新建目录
        folderMk();
        File f = new File(qrPath);
//        IMsgHandlerFace msgHandler = new SimpleDemo(); // 实现IMsgHandlerFace接口的类
        Wechat wechat = new Wechat(simpleDemo, f.getAbsolutePath()); // 【注入】
        wechat.start();
    }
}
