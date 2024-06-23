package com.naraci.app.AdminManage.mapper;

import com.naraci.app.AdminManage.domain.SysChildrenMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.naraci.app.AdminManage.domain.SysMenu;

import java.util.List;

/**
* @author Zhaoyu
* @description 针对表【sys_children_menu】的数据库操作Mapper
* @createDate 2024-06-22 23:52:36
* @Entity com.naraci.app.AdminManage.domain.SysChildrenMenu
*/
public interface SysChildrenMenuMapper extends BaseMapper<SysChildrenMenu> {

    List<SysMenu> findMenuNameOrPath(String name, String displayName);
}




