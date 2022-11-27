package com.lxc.realm;

import com.lxc.entity.User;
import com.lxc.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Frank_lin
 * @date 2022/11/27
 */
@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 自定义授权方法
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("进入自定义授权方法");
        // 创建对象，封装当前登录用户的角色权限信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 获取用户身份信息
        String principal = principals.getPrimaryPrincipal().toString();
        // 调用业务层获取用户的角色信息
        List<String> userRoleInfo = userService.getUserRoleInfo(principal);
        System.out.println("当前用户角色信息：" + userRoleInfo);

        // 获取用户的权限信息
        List<String> userPermissionInfo = userService.getUserPermissionInfo(userRoleInfo);
        System.out.println("当前用户权限信息：" + userPermissionInfo);

        // 存储角色
        info.addRoles(userRoleInfo);
        info.addStringPermissions(userPermissionInfo);
        // 返回角色信息

        return info;
    }

    /**
     * 自定义登录认证方法
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取用户身份信息
        String principal = token.getPrincipal().toString();
        // 调用业务层获取用户信息（数据库）
        User user = userService.getUserInfoByName(principal);
        // 非空判断 将数据完整封装
        if (user != null) {
            AuthenticationInfo info = new SimpleAuthenticationInfo(token.getPrincipal(), user.getPwd(), ByteSource.Util.bytes("salt"), principal);
            return info;
        }
        return null;
    }
}
