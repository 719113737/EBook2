package com.example.demo.security.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 好像是写token检验失败逻辑，现在不知道用在哪
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint{


    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        System.out.println("验证失败");
        httpServletResponse.sendError(HttpServletResponse.SC_ACCEPTED,"Error: Unauthorized");
    }
}
