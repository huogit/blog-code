package com.prestrive.blog.util;

import com.prestrive.blog.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {

    public static AccountProfile getProfile() {
        System.out.println("ShiroUtil:getProfile.....");

        // 获取 doGetAuthenticationInfo方法里 SimpleAuthenticationInfo里存的第一个参数
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }

}
