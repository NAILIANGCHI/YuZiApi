package com.naraci.core.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author ShenZhaoYu
 * @date 2024/3/3
 */
public class TimeUtils {

    /**
     *  时间字符串 转LocalDateTime
     * @param localDateTime  时间字符串
     * @return LocalDateTime
     */
    public static LocalDateTime stringToFormat(String localDateTime){
        String format  = "yyyy:MM:dd HH:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(localDateTime,dateTimeFormatter);
    }

    /**
     * 毫秒级时间戳转 LocalDateTime
     * @param epochMilli 毫秒级时间戳
     * @return LocalDateTime
     */
    public static LocalDateTime ofEpochMilli(long epochMilli){
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZoneOffset.of("+8"));
    }

    /**
     * 秒级时间戳转 LocalDateTime
     * @param epochSecond 秒级时间戳
     * @return LocalDateTime
     */
    public static LocalDateTime ofEpochSecond(long epochSecond){
        return LocalDateTime.ofEpochSecond(epochSecond, 0,ZoneOffset.of("+8"));
    }
}
