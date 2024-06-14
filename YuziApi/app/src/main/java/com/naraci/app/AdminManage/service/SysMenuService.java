package com.naraci.app.AdminManage.service;

import com.naraci.app.AdminManage.domain.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.naraci.app.AdminManage.entity.request.AddMenu;
import jakarta.validation.Valid;

/**
* @author Zhaoyu
* @description 针对表【sys_menu】的数据库操作Service
* @createDate 2024-06-11 23:52:15
*/
public interface SysMenuService extends IService<SysMenu> {

    void addMenu(@Valid AddMenu request);
}