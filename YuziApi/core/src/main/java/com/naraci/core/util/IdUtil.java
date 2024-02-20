package com.naraci.core.util;

import cn.hutool.core.lang.Snowflake;
import org.springframework.stereotype.Component;

/**
 * @author ShenZhaoYu
 * @date 2024/2/14
 */
@Component
public class IdUtil {
    private static Snowflake snowflake;
    public static String getSnowflakeId() {
        if (snowflake == null) {
            snowflake = new Snowflake();
        }
        return snowflake.nextIdStr();
    }
}
