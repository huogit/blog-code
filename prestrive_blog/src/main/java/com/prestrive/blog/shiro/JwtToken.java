package com.prestrive.blog.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Jwt
 * shiro 提供的自定义授权凭证
 * @author fanfanli
 * @date  2021/5/28
 */
public class JwtToken implements AuthenticationToken {
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}

