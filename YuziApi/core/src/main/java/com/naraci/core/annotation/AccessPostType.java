package com.naraci.core.annotation;

import com.naraci.app.entity.enums.RoleEnum;

import java.lang.annotation.*;

/**
 * @author ShenZhaoYu
 * @date 2024/3/9
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.TYPE_PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessPostType {
    RoleEnum.RoleEnums[] value();
}
