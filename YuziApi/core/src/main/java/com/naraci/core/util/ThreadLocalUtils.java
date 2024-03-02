package com.naraci.core.util;

/** 本地线程变量
 * @author ShenZhaoYu
 * @date 2024/3/1
 */
public class ThreadLocalUtils {
    // 提供线程全局对象
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal<>();

    // 根据键获取值
    public static <T> T get() {
        return (T) THREAD_LOCAL.get();
    }

    // 存储键值对
    public static void set(Object value) {
        THREAD_LOCAL.set(value);
    }

    // 清楚ThreadLocal 防止内存泄露
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
