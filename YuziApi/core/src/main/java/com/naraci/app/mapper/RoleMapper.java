package com.naraci.app.mapper;

import com.naraci.app.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author Zhaoyu
* @description 针对表【role】的数据库操作Mapper
* @createDate 2024-03-02 15:46:43
* @Entity com.naraci.app.domain.Role
*/
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> findAll();

    @Select("select * from role where role = #{name}")
    Role selectByRole(String name);

    @Select("SELECT * FROM role WHERE id = #{roleId}")
    Role selectId(String roleId);
}




