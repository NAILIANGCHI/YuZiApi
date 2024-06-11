package com.naraci.app.mapper;

import com.naraci.app.domain.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
* @author Zhaoyu
* @description 针对表【sys_user】的数据库操作Mapper
* @createDate 2024-02-15 11:35:14
* @Entity com.naraci.app.domain.SysUser
*/
public interface SysUserMapper extends BaseMapper<SysUser> {
    @Select("select * from sys_user where email = #{email} and is_deleted = 0")
    SysUser selectByEmail(String email);
}




