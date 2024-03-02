package com.naraci.app.service;

import com.naraci.app.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.naraci.app.entity.response.LoginResponse;
import com.naraci.app.entity.reuqest.AddUserRequest;
import com.naraci.app.entity.reuqest.LoginRequest;
import com.naraci.app.entity.reuqest.SysUserRegisterRequest;
import com.naraci.core.entity.UserInfo;
import org.apache.catalina.User;

/**
* @author Zhaoyu
* @description 针对表【sys_user】的数据库操作Service
* @createDate 2024-02-15 11:35:14
*/
public interface SysUserService extends IService<SysUser> {

    void register(SysUserRegisterRequest request);

    void sendMessage(UserInfo userInfo);

    LoginResponse login(LoginRequest request);

    void addUser(AddUserRequest request);

    SysUser userInfo();
}
