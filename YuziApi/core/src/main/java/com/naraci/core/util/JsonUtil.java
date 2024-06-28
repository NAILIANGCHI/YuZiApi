package com.naraci.core.util;

import com.google.gson.Gson;

/**
 * Json处理工具
 * 使用Gson
 * @author zuozhiwei
 */
public class JsonUtil {
    /**
     * 静态Gson对象
     */
    private static final Gson GSON = new Gson();

    /**
     * 对象转json
     * @param o 待转换对象
     * @return  json字符串
     */
    public static String toJson(Object o) {
        return GSON.toJson(o);
    }

    public static Gson getGson() {
        return GSON;
    }
}
