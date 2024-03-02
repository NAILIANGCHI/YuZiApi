package com.naraci.app.entity.enums;

import com.naraci.app.domain.Role;
import com.naraci.app.entity.response.ItemVo;
import com.naraci.app.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ShenZhaoYu
 * @date 2024/3/2
 */
@Service
public class RoleEnum {
    @Autowired
    private static RoleMapper roleMapper;

    public static List<ItemVo> pullDown() {
        List<Role> roleList = roleMapper.findAll();

      return roleList.stream().map(v -> {
            ItemVo itemVo = new ItemVo();
            itemVo.setId(v.getId());
            itemVo.setName(v.getName());
            return itemVo;
        }).collect(Collectors.toList());
    }
}
