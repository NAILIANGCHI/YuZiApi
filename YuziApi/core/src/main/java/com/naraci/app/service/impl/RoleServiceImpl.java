package com.naraci.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.naraci.app.domain.Role;
import com.naraci.app.service.RoleService;
import com.naraci.app.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author Zhaoyu
* @description 针对表【role】的数据库操作Service实现
* @createDate 2024-03-02 15:46:43
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




