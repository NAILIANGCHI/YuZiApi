package com.naraci.app.entity.enums;

import com.naraci.app.domain.Role;
import com.naraci.app.entity.response.ItemVo;
import com.naraci.app.mapper.RoleMapper;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ShenZhaoYu
 * @date 2024/3/2
 */
public class RoleEnum {

    public enum RoleEnums {
        Admin("超级管理员"),

        User("普通用户"),

        Service("客服");

        @Getter
        final String value;
        RoleEnums(String value) {
            this.value = value;
        }

    }

    @Resource
    private static RoleMapper roleMapper;

    // 从数据库取来的角色
    public static List<ItemVo> pullDown() {
        List<Role> roleList = roleMapper.findAll();

      return roleList.stream().map(v -> {
            ItemVo itemVo = new ItemVo();
            itemVo.setId(v.getId());
            itemVo.setName(v.getName());
            return itemVo;
        }).collect(Collectors.toList());
    }

    //从上述枚举取得角色
    public static List<ItemVo> pullDownListLocal()  {
        List<ItemVo> itemVos = new ArrayList<>();
        itemVos = Arrays.stream(RoleEnums.values()).map(tyoe -> {
            ItemVo itemVo = new ItemVo();
            itemVo.setId(tyoe.name());
            itemVo.setName(tyoe.getValue());
            return itemVo;
        }).collect(Collectors.toList());
        return itemVos;
    }

    /**
     * 根据值来获取枚举
     * @param value
     * @return
     */
    public static RoleEnums getEnumByValue(String value) {
        for (RoleEnums e : RoleEnums.values()) {
            if (e.getValue().equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }

    /**
     * 根据name来获取枚举
     * @param name
     * @return
     */
    public static RoleEnums getEnumByName(String name) {
        for (RoleEnums e : RoleEnums.values()) {
            if (e.name().equalsIgnoreCase(name)) {
                return e;
            }
        }
        return null;
    }
}
