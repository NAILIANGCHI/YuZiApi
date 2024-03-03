package com.naraci.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ShenZhaoYu
 * @date 2024/3/3
 */
public class StringUtils {


    /**
     * 提取文本中的数字工具
     * @param text
     * @return
     */
    public static String RequestNumber(String text) {

        Pattern pattern = Pattern.compile("\\d+");
        // 创建 Matcher 对象，用于在文本中搜索匹配的内容
        Matcher matcher = pattern.matcher(text);
        // 查找匹配的数字并输出
        String number = null;
        while (matcher.find()) {
            number = matcher.group();
        }
        return number;
    }
}
