package com.example.demo.security.jwt;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拦截token
 */
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String jwt = null;
        try {
            //获取请求头中的token
            String headerAuth = httpServletRequest.getHeader("Authorization");
            if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
                jwt = headerAuth.substring(7,headerAuth.length());
            }

            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                System.out.println("token验证成功");
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                //Thread.sleep(5000);
            }

        } catch (Exception e){
            System.out.println("拦截到非法token");
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
