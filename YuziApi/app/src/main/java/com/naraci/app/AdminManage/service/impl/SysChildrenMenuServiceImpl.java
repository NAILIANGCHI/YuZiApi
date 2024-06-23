package com.naraci.app.AdminManage.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naraci.app.AdminManage.domain.SysChildrenMenu;
import com.naraci.app.AdminManage.domain.SysMenu;
import com.naraci.app.AdminManage.entity.request.AddChildrenMenu;
import com.naraci.app.AdminManage.mapper.SysMenuMapper;
import com.naraci.app.AdminManage.service.SysChildrenMenuService;
import com.naraci.app.AdminManage.mapper.SysChildrenMenuMapper;
import com.naraci.core.aop.CustomException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Zhaoyu
* @description 针对表【sys_children_menu】的数据库操作Service实现
* @createDate 2024-06-22 23:52:36
*/
@Service
public class SysChildrenMenuServiceImpl extends ServiceImpl<SysChildrenMenuMapper, SysChildrenMenu>
    implements SysChildrenMenuService{

    @Resource
    private  SysChildrenMenuMapper sysChildrenMenuMapper;

    @Resource
    private SysMenuMapper sysMenuMapper;
    @Override
    public void addChildrenMenu(AddChildrenMenu request) {
        // 判断父类id 是否合法
        SysMenu mainMenu = sysMenuMapper.selectById(request.getMainId());
        if (ObjUtil.isEmpty(mainMenu)) {
            throw new CustomException("添加异常，父类菜单不存在。");
        }
        // 判断该菜单是否存在
        List<SysMenu> findSysMenu = sysChildrenMenuMapper.findMenuNameOrPath(request.getName(),request.getDisplayName());

        if (!ObjUtil.isEmpty(findSysMenu)) {
            throw new CustomException("菜单名或路径已存在");
        }
        SysChildrenMenu sysChildrenMenu = new SysChildrenMenu();
        sysChildrenMenu.setDisplayName(request.getDisplayName());
        sysChildrenMenu.setMainId(request.getMainId());
        sysChildrenMenu.setName(request.getName());
        sysChildrenMenuMapper.insert(sysChildrenMenu);
    }

    @Override
    public void updateChildrenMenuCheck(String id) {
        if (id == null) {
            throw new CustomException("id不能为空");
        }

        SysChildrenMenu sysChildrenMenu = sysChildrenMenuMapper.selectById(id);
        boolean isStart = sysChildrenMenu.getIsStart();
        sysChildrenMenu.setIsStart(!isStart);
        sysChildrenMenuMapper.updateById(sysChildrenMenu);
    }

    @Override
    public void delChildrenMenu(String id) {
        SysChildrenMenu sysChildrenMenu = sysChildrenMenuMapper.selectById(id);
        if (ObjUtil.isEmpty(sysChildrenMenu)) {
            throw new CustomException("删除失败：id不存在");
        }
        sysChildrenMenuMapper.deleteById(sysChildrenMenu);
    }

    @Override
    public List<SysChildrenMenu> getMenuList(String id) {
        SysMenu sysMenu = sysMenuMapper.selectById(id);
        if (ObjUtil.isEmpty(sysMenu)) {
            throw new CustomException("获取子类菜单失败请刷新");
        }

        return sysChildrenMenuMapper.selectList(
                Wrappers.lambdaQuery(SysChildrenMenu.class)
                        .eq(SysChildrenMenu::getMainId, id)
        ).stream().toList();
    }
}




