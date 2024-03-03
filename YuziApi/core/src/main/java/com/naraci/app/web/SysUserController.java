package com.naraci.app.web;

import com.naraci.app.domain.Role;
import com.naraci.app.entity.response.ItemVo;
import com.naraci.app.entity.response.LoginResponse;
import com.naraci.app.entity.reuqest.AddUserRequest;
import com.naraci.app.entity.reuqest.LoginRequest;
import com.naraci.app.entity.reuqest.SysUserRegisterRequest;
import com.naraci.app.mapper.RoleMapper;
import com.naraci.app.service.SysUserService;
import com.naraci.core.entity.UserInfo;
import com.naraci.core.util.JwtUtils;
import com.naraci.core.util.RedisUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ShenZhaoYu
 * @date 2024/2/14
 */
@RestController
@Tag(name = "用户基础服务")
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RoleMapper roleMapper;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public void register(
            @RequestBody @Valid SysUserRegisterRequest request
    ) {
        sysUserService.register(request);
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody @Valid LoginRequest request
    ) {
        return sysUserService.login(request);
    }

    @Operation(summary = "发送验证码")
    @PostMapping("/sendMessage")
    public void sendMessage(
            @RequestAttribute(JwtUtils.TOKEN)UserInfo userInfo
            ) {
        sysUserService.sendMessage(userInfo);
    }

    @Operation(summary = "新增用户")
    @PostMapping("/add")
    public void add(
            @RequestBody @Valid AddUserRequest request
    ) {
        sysUserService.addUser(request);
    }

    @Operation(summary = "查看角色列表")
    @PostMapping("/role/list")
    public List<ItemVo> roleList() {
        List<Role> roleList = roleMapper.findAll();

        return roleList.stream().map(v -> {
            ItemVo itemVo = new ItemVo();
            itemVo.setId(v.getId());
            itemVo.setName(v.getName());
            return itemVo;
        }).collect(Collectors.toList());
    }

}
