package com.lxc.service;

import com.lxc.entity.User;

import java.util.List;

/**
 * @author Frank_lin
 * @date 2022/11/27
 */
public interface UserService {
    /**
     * 用户登录
     */
    User getUserInfoByName(String name);

    /**
     * 根据用户查询角色信息
     * @param name
     * @return
     */
    List<String> getUserRoleInfo(String name);

    /**
     * 获取用户角色权限信息
     * @param roles
     * @return
     */
    List<String> getUserPermissionInfo(List<String> roles);
}
