package com.naraci.app.AdminManage.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.naraci.app.domain.SysUser;
import com.naraci.app.mapper.SysUserMapper;
import com.naraci.app.media.domain.DouyinCount;
import com.naraci.app.media.mapper.DouyinCountMapper;
import com.naraci.core.aop.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ShenZhaoYu
 * @date 2024/3/3
 */
@Service
public class QuoTaService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private DouyinCountMapper douyinCountMapper;
    public void addDouyin(String email) {
        SysUser sysUser = sysUserMapper.selectByEmail(email);
        if (ObjectUtils.isEmpty(sysUser)) {
            throw new CustomException("该用户不存在!");
        }
       DouyinCount douyinCount = douyinCountMapper.selectByUserId(sysUser.getId());
        if (ObjectUtils.isEmpty(douyinCount)) {
            throw new CustomException("该用户没有相关记录,请联系管理员处理");
        }
        douyinCount.setCount(10);
        douyinCountMapper.updateById(douyinCount);
    }
}
