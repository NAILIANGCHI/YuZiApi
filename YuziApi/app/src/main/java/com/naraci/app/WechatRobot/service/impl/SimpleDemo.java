package com.naraci.app.WechatRobot.service.impl;

import com.naraci.app.WechatRobot.api.MessageTools;
import com.naraci.app.WechatRobot.beans.BaseMsg;
import com.naraci.app.WechatRobot.beans.RecommendInfo;
import com.naraci.app.WechatRobot.face.IMsgHandlerFace;
import com.naraci.app.WechatRobot.utils.FolderUtils;
import com.naraci.app.WechatRobot.utils.enums.MsgTypeEnum;
import com.naraci.app.WechatRobot.utils.tools.DownloadTools;
import com.naraci.app.media.entity.request.SrcRequest;
import com.naraci.app.media.entity.response.DouyinVideoResponse;
import com.naraci.app.media.entity.response.WeiBoHotResponse;
import com.naraci.app.media.service.impl.MediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 简单示例程序，收到文本信息自动回复原信息，收到图片、语音、小视频后根据路径自动保存
 *
 * @author https://github.com/yaphone
 * @date 创建时间：2017年4月25日 上午12:18:09
 * @version 1.0
 *
 */
@Slf4j
@Service
public class SimpleDemo implements IMsgHandlerFace {
	@Autowired
	private MediaService mediaService;
	private final String folderDir = FolderUtils.folderDirName();

	@Override
	public String textMsgHandle(BaseMsg msg) throws Exception {
		// 判断用户消息类型 私聊还是群组
		if (!msg.isGroupMsg()) {
			String text = msg.getText();
			if (text.equals("菜单")) {
				return "\t \t------菜单------\n抖音视频解析 | 微博热搜前20 \n 输入上面的指令来使用功能";
			} else if (text.equals("微博热搜前20")) {
				List<WeiBoHotResponse> weibo = mediaService.weibo();
				StringBuilder weiboText = new StringBuilder();
				for (int i = 0; i < weibo.size(); i++ ) {
					WeiBoHotResponse w = weibo.get(i);
					weiboText.append(w.getRank()).append(".").append(w.getTitle()).append("\n 类型:").append(w.getType())
							.append("\n 热度:").append(w.getHot()).append("\n 时间:").append(w.getTime()).append("\n \n");
				}
				return weiboText.toString();
			}else if (msg.getText().equals("抖音视频解析")) {
				return "抖音 + 视频分享文案";
			} else if (msg.getText().startsWith("抖音")) {

				SrcRequest srcRequest = new SrcRequest();
				srcRequest.setUrl(msg.getText());
				DouyinVideoResponse douyinVideoResponse = mediaService.douyinVideo(srcRequest);
				return "视频标题:" + douyinVideoResponse.getTitle() + "\n MP3:"
						+ douyinVideoResponse.getMp3() + "\n 视频链接:" + douyinVideoResponse.getMp4();
			}
		}
		return null;
	}

	@Override
	public String picMsgHandle(BaseMsg msg) {
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());// 这里使用收到图片的时间作为文件名
		String picPath = folderDir +"/pic" + File.separator + fileName + ".jpg"; // 调用此方法来保存图片
		DownloadTools.getDownloadFn(msg, MsgTypeEnum.PIC.getType(), picPath); // 保存图片的路径
//		return "图片保存成功";
		return null;
	}

	@Override
	public String voiceMsgHandle(BaseMsg msg) {
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		String voicePath = folderDir + "/voice" + File.separator + fileName + ".mp3";
		DownloadTools.getDownloadFn(msg, MsgTypeEnum.VOICE.getType(), voicePath);
//		return "声音保存成功";
		return null;
	}

	@Override
	public String viedoMsgHandle(BaseMsg msg) {
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		String viedoPath = folderDir + "/video" + File.separator + fileName + ".mp4";
		DownloadTools.getDownloadFn(msg, MsgTypeEnum.VIEDO.getType(), viedoPath);
//		return "视频保存成功";
		return null;
	}

	@Override
	public String nameCardMsgHandle(BaseMsg msg) {
		return "收到名片消息";
	}

	@Override
	public void sysMsgHandle(BaseMsg msg) { // 收到系统消息
		String text = msg.getContent();
		log.info(text);
	}

	@Override
	public String verifyAddFriendMsgHandle(BaseMsg msg) {
		MessageTools.addFriend(msg, true); // 同意好友请求，false为不接受好友请求
		RecommendInfo recommendInfo = msg.getRecommendInfo();
		String nickName = recommendInfo.getNickName();
//		String province = recommendInfo.getProvince();
//		String city = recommendInfo.getCity();
		return "你好," + nickName + "\n \n 输入\\menu来使用我";
	}

	@Override
	public String mediaMsgHandle(BaseMsg msg) {
		String fileName = msg.getFileName();
		String filePath = folderDir + "/file" + File.separator + fileName; // 这里是需要保存收到的文件路径，文件可以是任何格式如PDF，WORD，EXCEL等。
		DownloadTools.getDownloadFn(msg, MsgTypeEnum.MEDIA.getType(), filePath);
//		return "文件" + fileName + "保存成功";
		return null;
	}

}
