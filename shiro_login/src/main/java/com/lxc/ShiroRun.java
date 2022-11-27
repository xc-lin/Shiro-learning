package com.lxc;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.env.BasicIniEnvironment;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

/**
 * @author Frank_lin
 * @date 2022/11/27
 */
public class ShiroRun {
    public static void main(String[] args) {
        // 1、初始化获取securityManager
        BasicIniEnvironment basicIniEnvironment = new BasicIniEnvironment("classpath:shiro.ini");
        SecurityManager securityManager = basicIniEnvironment.getSecurityManager();
        SecurityUtils.setSecurityManager(securityManager);
        // 2、获取subject对象
        Subject subject = SecurityUtils.getSubject();
        // 3、创建token对象，web应用用户名密码从页面传递
        AuthenticationToken token = new UsernamePasswordToken("zhangsan", "z3");
        // 4、完成登录
        try {
            subject.login(token);
            System.out.println("登录成功");
            // 5、判断角色
            boolean role1 = subject.hasRole("role1");
            System.out.println("是否拥有此角色="+role1);
            // 6、判断权限
            boolean permitted = subject.isPermitted("user:select");
            System.out.println("是否拥有此权限="+permitted);

            subject.checkPermission("user:select");

        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户不存在");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码错误");
        } catch (AuthenticationException ae) { //unexpected condition? error?
        }

    }
}
