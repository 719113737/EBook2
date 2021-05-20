package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @RequestMapping(value = "/user_test",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Map userTest() {
        Map<String,Object> result = new HashMap<>();
        result.put("msg","");
        result.put("code",200);

        return result;
    }

    @RequestMapping(value = "/admin_test",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Map adminTest() {
        Map<String,Object> result = new HashMap<>();
        result.put("msg","");
        result.put("code",200);

        return result;
    }
}
