package com.example.demo.service;

import com.example.demo.mapper.SecurityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Security服务
 */
@Service
public class SecurityService {

    @Autowired
    SecurityMapper securityMapper;

    /**
     * 获得能访问该url的角色权限
     * @param url 要访问的url
     * @return 能够访问该url的权限集合List
     */
    public List<String> getRoleByUrl(String url) {
        return new ArrayList<>();
    }
}
