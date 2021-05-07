package com.example.demo.security;

import com.example.demo.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 自定义的Metadata，通过当前请求地址，获取该地址所需要的角色
 */
@Service
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    @Autowired
    SecurityService securityService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {

        //object中包含用户请求request信息
        HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
        String url = request.getRequestURI();
        List<String> roleNameList = securityService.getRoleByUrl(url);
        if(roleNameList!=null) {
            List<ConfigAttribute> attributes = new ArrayList<>();
            for (String rolename:roleNameList) {
                attributes.add(new SecurityConfig(rolename));
            }
            return attributes;
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
