package com.naraci.app.AdminManage.mapper;

import com.naraci.app.AdminManage.domain.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Zhaoyu
* @description 针对表【sys_menu】的数据库操作Mapper
* @createDate 2024-06-11 23:52:15
* @Entity com.naraci.app.AdminManage.domain.SysMenu
*/
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    SysMenu findMenuNameOrPath(String name, String displayName);
}




