package com.lxc.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author Frank_lin
 * @date 2022/11/27
 */
@Controller
@RequestMapping("/myController")
public class MyController {

    //跳转登录页面
    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("/userLogin")
    public String userLogin(String name, String pwd, @RequestParam(defaultValue = "false") boolean rememberMe, HttpSession session) {
        // 获取subject对象
        Subject subject = SecurityUtils.getSubject();
        // 封装token
        AuthenticationToken token = new UsernamePasswordToken(name, pwd, rememberMe);
        // 调用login进行登录
        try {
            subject.login(token);
            session.setAttribute("user", token.getPrincipal().toString());
            return "main";
        } catch (Exception e) {
            System.out.println("登录失败");
            return "登录失败";
        }

    }

    //登录认证验证 rememberMe
    @GetMapping("userLoginRm")
    public String userLogin(HttpSession session) {
        session.setAttribute("user", "rememberMe");
        return "main";
    }

    // 登录认证验证角色
    @RequiresRoles("admin")
    @ResponseBody
    @GetMapping("userLoginRoles")
    public String userLoginRoles() {
        System.out.println("登录认证验证角色");
        return "验证成功";
    }

    //登录认证验证权限
    @RequiresPermissions("user:delete")
    @GetMapping("userPermissions")
    @ResponseBody
    public String userLoginPermissions() {
        System.out.println("登录认证验证权限");
        return "验证权限成功";
    }


}
