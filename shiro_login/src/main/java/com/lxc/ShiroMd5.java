package com.lxc;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author Frank_lin
 * @date 2022/11/27
 */
public class ShiroMd5 {
    public static void main(String[] args) {
        String password = "l4";
        Md5Hash md5Hash = new Md5Hash(password);
        System.out.println("md5加密：" + md5Hash);
        // 带盐的 md5 加密，盐就是在密码明文后拼接新字符串，然后再进行加密
        Md5Hash md5Hash2 = new Md5Hash(password,"salt",3);
        System.out.println("md5带盐加密：" + md5Hash2);


        // 是否父类进行加密
        SimpleHash simpleHash = new SimpleHash("MD5",password,"salt",3);

        System.out.println("simpleHash: "+simpleHash);

    }
}
