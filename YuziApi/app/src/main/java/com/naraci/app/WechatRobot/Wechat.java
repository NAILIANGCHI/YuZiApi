package com.naraci.app.WechatRobot;

import com.naraci.app.WechatRobot.controller.LoginController;
import com.naraci.app.WechatRobot.core.MsgCenter;
import com.naraci.app.WechatRobot.face.IMsgHandlerFace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.naraci.app.WechatRobot.api.WechatTools.getContactNickNameList;


public class Wechat {
	private static final Logger LOG = LoggerFactory.getLogger(Wechat.class);
	private final IMsgHandlerFace msgHandler;

	public Wechat(IMsgHandlerFace msgHandler, String qrPath) {
		System.setProperty("jsse.enableSNIExtension", "false"); // 防止SSL错误
		this.msgHandler = msgHandler;

		// 登陆
		LoginController login = new LoginController();
		login.login(qrPath);
	}

	public void start() {
		LOG.info("+++++++++++++++++++开始消息处理+++++++++++++++++++++");
		List<String> contactNickNameList = getContactNickNameList();
		LOG.info(new ArrayList<>(contactNickNameList).toString());
		new Thread(new Runnable() {
			@Override
			public void run() {
				MsgCenter.handleMsg(msgHandler);
			}
		}).start();
	}

}
