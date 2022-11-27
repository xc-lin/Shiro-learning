package com.lxc.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.lxc.realm.MyRealm;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

/**
 * @author Frank_lin
 * @date 2022/11/27
 */
@Configuration
public class ShiroConfig {

    @Autowired
    private MyRealm myRealm;

    //配置 SecurityManager
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        // 创建defaultWebSecurityManager
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // 创建加密对象，设置相关属性
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 采用md5加密
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        // 采用的迭代加密次数
        hashedCredentialsMatcher.setHashIterations(3);
        // 将加密对象，存储到myRealm中
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        // 将myRealm 存入defaultWebSecurityManager中
        defaultWebSecurityManager.setRealm(myRealm);
        // 设置rememberMe
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        // 设置缓存管理器
        defaultWebSecurityManager.setCacheManager(getEhCacheManager());
        // 返回
        return defaultWebSecurityManager;
    }

    private EhCacheManager getEhCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        InputStream is = null;
        try {
            is = ResourceUtils.getInputStreamForPath(
                    "classpath:ehcache/ehcache-shiro.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        CacheManager cacheManager = new CacheManager(is);
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;

    }

    //cookie 属性设置
    public SimpleCookie rememberMeCookie() {
        SimpleCookie cookie = new SimpleCookie("rememberMe"); //设置跨域
        //cookie.setDomain(domain);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30 * 24 * 60 * 60);
        return cookie;
    }

    //创建 Shiro 的 cookie 管理对象
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey("1234567890987654".getBytes());
        return cookieRememberMeManager;
    }


    //配置 Shiro 内置过滤器拦截范围
    @Bean
    public DefaultShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        //设置不认证可以访问的资源
        definition.addPathDefinition("/myController/userLogin", "anon");
        definition.addPathDefinition("/login", "anon");
        //添加存在用户的过滤器(rememberMe)
        definition.addPathDefinition("/**", "user");
        //配置登出过滤器
        definition.addPathDefinition("/logout", "logout");
        //设置需要进行登录认证的拦截范围
        definition.addPathDefinition("/**", "authc");
        return definition;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
