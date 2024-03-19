package com.naraci.app.WechatRobot.service.impl;

import com.naraci.app.WechatRobot.face.IMsgHandlerFace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author https://github.com/yaphone
 * @date 创建时间：2017年4月28日 上午12:44:10
 * @version 1.0
 *
 */
@Component
public class WeChatRobotMainService {
	@Autowired
	private IMsgHandlerFace simpleDemo = new WechatModelService();

//	public void mainWechat() {
//		String qrPath = "D:\\Zhaoyu\\Chtgpt\\itchat4j-uos\\src\\main\\resources"; // 保存登陆二维码图片的路径，这里需要在本地新建目录
//		folderMk();
//		File f = new File(qrPath);
////		IMsgHandlerFace msgHandler = new SimpleDemo(); // 实现IMsgHandlerFace接口的类
//		Wechat wechat = new Wechat(simpleDemo, f.getAbsolutePath()); // 【注入】
//		wechat.start(); // 启动服务，会在qrPath下生成一张二维码图片，扫描即可登陆，注意，二维码图片如果超过一定时间未扫描会过期，过期时会自动更新，所以你可能需要重新打开图片
//	}
}
