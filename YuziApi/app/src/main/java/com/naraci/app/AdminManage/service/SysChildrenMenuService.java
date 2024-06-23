package com.naraci.app.AdminManage.service;

import com.naraci.app.AdminManage.domain.SysChildrenMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.naraci.app.AdminManage.entity.request.AddChildrenMenu;

import java.util.List;

/**
* @author Zhaoyu
* @description 针对表【sys_children_menu】的数据库操作Service
* @createDate 2024-06-22 23:52:36
*/
public interface SysChildrenMenuService extends IService<SysChildrenMenu> {

    void addChildrenMenu(AddChildrenMenu request);

    void updateChildrenMenuCheck(String id);

    void delChildrenMenu(String id);

    List<SysChildrenMenu> getMenuList(String id);
}
