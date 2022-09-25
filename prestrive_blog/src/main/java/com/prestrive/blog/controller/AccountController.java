package com.prestrive.blog.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.prestrive.blog.common.lang.Result;
import com.prestrive.blog.common.lang.dto.LoginDto;
import com.prestrive.blog.entity.User;
import com.prestrive.blog.service.UserService;
import com.prestrive.blog.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
/**
 * 登录登出控制器
 *
 * @author fanfanli
 * @date  2021/4/8
 */
@RestController
public class AccountController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    /**
     * 登录请求处理
     */
    @CrossOrigin
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {
        System.out.println("AccountController:login");
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        //Assert：断言，判断传进来的参数值是否不为空值,否则会抛异常
        Assert.notNull(user, "用户名或密码错误");
        //加密解密工具-SecureUtil：安全
        if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return Result.fail("用户名或密码错误！");
        }
        if(user.getStatus()==0){
            return Result.fail("账户已被禁用");
        }
        //生成token
        String jwt = jwtUtils.generateToken(user.getId(),user.getUsername());
        //设置响应头
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        // 用户可以另一个接口
        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .put("role", user.getRole())
                .map()
        );
    }

    /**
     * 登出请求处理
     */
    @GetMapping("/logout")
    //@RequiresAuthentication 验证用户是否登录
    @RequiresAuthentication
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ("退出成功");
    }
}
