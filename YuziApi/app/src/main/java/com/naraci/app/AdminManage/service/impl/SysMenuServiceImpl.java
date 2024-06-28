package com.naraci.app.AdminManage.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naraci.app.AdminManage.domain.SysChildrenMenu;
import com.naraci.app.AdminManage.domain.SysMenu;
import com.naraci.app.AdminManage.entity.request.AddMenu;
import com.naraci.app.AdminManage.entity.response.ChildrenMenu;
import com.naraci.app.AdminManage.entity.response.MenuRouter;
import com.naraci.app.AdminManage.mapper.SysChildrenMenuMapper;
import com.naraci.app.AdminManage.service.SysMenuService;
import com.naraci.app.AdminManage.mapper.SysMenuMapper;
import com.naraci.core.aop.CustomException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author Zhaoyu
* @description 针对表【sys_menu】的数据库操作Service实现
* @createDate 2024-06-11 23:52:15
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService{

    @Resource SysMenuMapper sysMenuMapper;
    @Resource
    private SysChildrenMenuMapper sysChildrenMenuMapper;
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

    @Override
    public void updateMenCheck(String id) {

        if (id == null) {
            throw new CustomException("id不能为空");
        }

        SysMenu sysMenu = sysMenuMapper.selectById(id);
        boolean isStart = sysMenu.getIsStart();
        sysMenu.setIsStart(!isStart);
        sysMenuMapper.updateById(sysMenu);
    }

    @Override
    public void delMenu(String id) {
        SysMenu sysMenu = sysMenuMapper.selectById(id);
        if (ObjUtil.isEmpty(sysMenu)) {
            throw new CustomException("删除失败：id不存在");
        }
        sysMenuMapper.deleteById(sysMenu);
    }

    @Override
    public List<MenuRouter> getMenuRouter() {
        // 获取全部主菜单
        List<SysMenu> menuList = sysMenuMapper.getMenuList();

        // 获取子菜单
        List<SysChildrenMenu> sysChildrenMenuList = sysChildrenMenuMapper.selectList(
                Wrappers.lambdaQuery()
        );

        Map<String, List<SysChildrenMenu>> sysMenuServiceMap = sysChildrenMenuList.stream().collect(Collectors.groupingBy(SysChildrenMenu::getMainId));
        return menuList.stream().map(item -> {
            MenuRouter menuRouter = new MenuRouter();
//            menuRouter.setId(item.getId());
            menuRouter.setKey(item.getName());
            menuRouter.setLabel(item.getDisplayName());
            menuRouter.setIcon(item.getIcon());
            if (!ObjUtil.isEmpty(sysMenuServiceMap.get(item.getId()))) {
                List<SysChildrenMenu> sysChildrenMenuList1 = sysMenuServiceMap.get(item.getId());
                List<ChildrenMenu> childrenMenus = sysChildrenMenuList1.stream().map(childrenItem -> {
                    ChildrenMenu childrenMenu = new ChildrenMenu();
                    childrenMenu.setLabel(childrenItem.getDisplayName());
                    childrenMenu.setKey(childrenItem.getName());
                    return childrenMenu;
                }).toList();
                menuRouter.setChildren(childrenMenus);
            }
            return menuRouter;
        }).toList();
    }
}




