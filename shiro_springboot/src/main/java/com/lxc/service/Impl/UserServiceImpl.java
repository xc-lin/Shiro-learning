package com.lxc.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lxc.entity.User;
import com.lxc.mapper.UserMapper;
import com.lxc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Frank_lin
 * @date 2022/11/27
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserInfoByName(String name) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("name",name);
        User user = userMapper.selectOne(userQueryWrapper);
        return user;
    }

    @Override
    public List<String> getUserRoleInfo(String name) {
        return userMapper.getUserRoleInfoMapper(name);
    }

    @Override
    public List<String> getUserPermissionInfo(List<String> roles) {
        return userMapper.getUserPermissionInfoMapper(roles);
    }
}
