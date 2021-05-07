package com.example.demo.security;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 实现登录认证逻辑
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserService user_service;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = (String)authentication.getCredentials();
        UserInfo userInfo = (UserInfo)user_service.loadUserByUsername(username);

        //判断用户名是否存在
        if (userInfo == null) {
            throw new BadCredentialsException("用户名不存在");
        }

        //判断密码是否正确
        if (!userInfo.getPassword().equals(password)) {
            throw new BadCredentialsException("密码不正确");
        }

        //session添加useranme
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().setAttribute("user",username);

        Collection< ? extends GrantedAuthority> authorities = userInfo.getAuthorities();
        //返回token
        return new UsernamePasswordAuthenticationToken(userInfo,password,authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        //表示支持该执行
        return true;
    }
}
