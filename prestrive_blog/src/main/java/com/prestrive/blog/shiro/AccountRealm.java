package com.prestrive.blog.shiro;

import cn.hutool.core.bean.BeanUtil;

import com.prestrive.blog.entity.User;
import com.prestrive.blog.service.UserService;
import com.prestrive.blog.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Shiro 从 Realm 获取安全数据（如用户、角色、权限）
 * 先到 shirofilter 过滤器 然后再到这
 * 登录认证和授权
 *
 * @author fanfanli
 * @date  2021/5/28
 */
@Slf4j
@Component
public class AccountRealm extends AuthorizingRealm {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    /**
     * Realm 是否 支持 传进来token
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        System.out.println("AccountRealm:supports......");

        return token instanceof JwtToken;
    }


    /**
     * 授权：只执行一次，即有用到@RequiresPermissions("user:create")，@RequiresRoles("role_root")时调用
     * 因为也间接继承了CachingRealm（带有缓存实现）
     * PrincipalCollection ： 这个是由 认证时 存入的
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("AccountRealm:doGetAuthorizationInfo 1......");

        log.info("执行doGetAuthorizationInfo方法进行授权");
//        String username = JwtUtil.getUsername(principalCollection.toString());
        log.info(principals.toString());
//        log.info("登录的用户:" + username);
        //生成 认证信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        AccountProfile accountProfile = (AccountProfile)principals.getPrimaryPrincipal();
        String[] roles = accountProfile.getRole().split(",");
        log.info("roles");
        for(String role : roles){
            //添加角色
            info.addRole(role);
            //添加权限
            if(role.equals("role_root")){
                info.addStringPermission("user:create");
                //info.addStringPermission("user:update");
                info.addStringPermission("user:read");
                info.addStringPermission("user:delete");
            }
            else if( role.equals("role_admin")){
                info.addStringPermission("user:read");
                info.addStringPermission("user:create");
                info.addStringPermission("user:update");
            }
            else if( role.equals("role_user")){
                info.addStringPermission("user:read");
                info.addStringPermission("user:create");
            }
            else if(role.equals("role_guest")){
                info.addStringPermission("user:read");
            }
        }


        return info;

    }

    /**
     * 认证，先认证后授权，认证时存入 PrincipalCollection
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("AccountRealm:doGetAuthenticationInfo 2......");

        //shiro 提供的自定义认证凭证
        JwtToken jwt = (JwtToken) token;
        log.info("jwt----------------->{}", jwt);
        //从token 中获取 用户标识
        String userId = (String) jwtUtils.getClaimByToken((String) jwt.getPrincipal()).get("userId");
        String username = (String) jwtUtils.getClaimByToken((String) jwt.getPrincipal()).get("username");
        // 查询用户信息
        User user = userService.getById(Long.parseLong(userId));
        if (user == null) {
            throw new UnknownAccountException("账户不存在！");
        }
        if (user.getStatus() == -1) {
            throw new LockedAccountException("账户已被锁定！");
        }
        if(!user.getUsername().equals(username)){
            throw new UnknownAccountException("userId与username不一致");
        }
        // 账户配置文件，用于shiro
        AccountProfile profile = new AccountProfile();
        // 知道它的身份 principals
        BeanUtil.copyProperties(user, profile);
        log.info("profile----------------->{}", profile.toString());
        return new SimpleAuthenticationInfo(profile, jwt.getCredentials(), getName());
    }
}

