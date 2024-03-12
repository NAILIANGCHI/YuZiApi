package com.naraci.app.BaseServer.mapper;

import com.naraci.app.BaseServer.domain.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* @author Zhaoyu
* @description 针对表【user_role】的数据库操作Mapper
* @createDate 2024-03-09 17:41:46
* @Entity com.naraci.app.BaseServer.domain.UserRole
*/
public interface UserRoleMapper extends BaseMapper<UserRole> {

    @Select("select * from user_role where user_id = #{id}")
    UserRole selectByUserid(String id);
}




