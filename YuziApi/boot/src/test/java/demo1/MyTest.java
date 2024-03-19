package demo1;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.naraci.app.BaseServer.domain.UserRole;
import com.naraci.app.WechatRobot.Wechat;
import com.naraci.app.WechatRobot.face.IMsgHandlerFace;
import com.naraci.app.domain.SysUser;
import com.naraci.app.mapper.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static com.naraci.app.WechatRobot.utils.FolderUtils.folderMk;

/**
 *
 * @author https://github.com/yaphone
 * @date 创建时间：2017年4月28日 上午12:44:10
 * @version 1.0
 *
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class MyTest {

	@Autowired
	private static IMsgHandlerFace msgHandler;
	@Autowired
	private SysUserMapper sysUserMapper;


	@Test
	public static void main(String[] args) {
		String qrPath = "D:\\Zhaoyu\\Chtgpt\\itchat4j-uos\\src\\main\\resources"; // 保存登陆二维码图片的路径，这里需要在本地新建目录
		folderMk();
		File f = new File(qrPath);
		msgHandler = new SimpleDemo(); // 实现IMsgHandlerFace接口的类
		Wechat wechat = new Wechat(msgHandler, f.getAbsolutePath()); // 【注入】
		wechat.start(); // 启动服务，会在qrPath下生成一张二维码图片，扫描即可登陆，注意，二维码图片如果超过一定时间未扫描会过期，过期时会自动更新，所以你可能需要重新打开图片
	}

	@Test
	public void test1() {
		List<SysUser> sysUsers = sysUserMapper.selectList(
				Wrappers.lambdaQuery(SysUser.class)
		);
		log.info(sysUsers.stream().toList().toString());
	}
}
