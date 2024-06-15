package com.naraci.app.AdminManage.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naraci.app.AdminManage.domain.SysMenu;
import com.naraci.app.AdminManage.entity.request.AddMenu;
import com.naraci.app.AdminManage.service.SysMenuService;
import com.naraci.app.AdminManage.mapper.SysMenuMapper;
import com.naraci.core.aop.CustomException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Zhaoyu
* @description 针对表【sys_menu】的数据库操作Service实现
* @createDate 2024-06-11 23:52:15
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService{

    @Resource SysMenuMapper sysMenuMapper;
    @Override
    public void addMenu(AddMenu request) {

        // 判断该菜单是否存在
        List<SysMenu> findSysMenu = sysMenuMapper.findMenuNameOrPath(request.getName(),request.getDisplayName());

        if (!ObjUtil.isEmpty(findSysMenu)) {
            throw new CustomException("菜单名或路径已存在");
        }
        SysMenu sysMenu = new SysMenu();
        sysMenu.setDisplayName(request.getDisplayName());
        sysMenu.setName(request.getName());
        sysMenu.setIcon(request.getIcon());
        sysMenuMapper.insert(sysMenu);
    }

    @Override
    public List<SysMenu> getMenuList() {
        return sysMenuMapper.getMenuList();

    }
}




