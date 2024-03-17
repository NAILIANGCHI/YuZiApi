package com.naraci.app.WechatRobot.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** 文件夹工具
 * @author ShenZhaoYu
 * @date 2024/3/17
 */
public class FolderUtils {

    private static String folderNames;


    public static String folderDirName() {
        String folderNames1 = folderNames;
        return folderNames1;
    }
    /**
     * 初始化 资源目录
     */
    public static void folderMk() {
        // 获取当前的路径
        String property = System.getProperty("user.dir");

        // 创建文件夹名称
        String folderName = "WechatMsgFile";

        // 构建文件夹
        Path floderPath = Paths.get(property, folderName);

        // 要创建的子目录名称数组
        String[] subDirectoryNames = {"pic", "video", "file", "voice","qc"};
        // 判断目录是否存在 存在跳过 不存在创建
        boolean folderEx = Files.exists(floderPath);
        if (folderEx) {
            System.out.println("文件夹已存在，跳过");
            folderNames = floderPath.toString();

        }else {
            try{
                Path directories = Files.createDirectories(floderPath);
                System.out.println("文件夹初始成功");
                // 创建子目录
                for (String subDirectoryName : subDirectoryNames) {
                    Path subDirectoryPath = directories.resolve(subDirectoryName);
                    Files.createDirectories(subDirectoryPath);
                    System.out.println("子目录已创建：" + subDirectoryPath);
                }
                folderNames = floderPath.toString();
            } catch (IOException e) {
                System.out.println("文件夹初始失败" + e.getMessage());
            }
        }
    }
}
