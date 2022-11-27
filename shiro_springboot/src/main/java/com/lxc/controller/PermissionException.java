package com.lxc.controller;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Frank_lin
 * @date 2022/11/27
 */
@RestControllerAdvice
public class PermissionException {

    @ExceptionHandler(UnauthorizedException.class)
    public String unauthorizedException(Exception e){
        return "无权限";
    }

    @ExceptionHandler(AuthorizationException.class)
    public String authorizationException(Exception e){
        return "权限认证失败";
    }

}
