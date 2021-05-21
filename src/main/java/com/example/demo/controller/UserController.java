package com.example.demo.controller;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "用户个人模块")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 注册
     */
    @Operation(description = "注册")
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public Map register(@RequestBody Map map) {
        boolean success = userService.registerUser((String) map.get("username"), (String) map.get("password"), (String) map.get("phone"));
        Map<String, Object> result = new HashMap<>();
        if (success) {
            result.put("msg", "success");
            result.put("code", 200);
        } else {
            result.put("msg", "failed");
            result.put("code", 404);
        }

        return result;
    }

    /**
     * 获得个人信息
     */
    @Operation(description = "个人信息")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @RequestMapping(path = "/user/{username}", method = RequestMethod.GET)
    public Map getInfoByUsername(@PathVariable("username") String username) {
        Map<String, Object> result = new HashMap<>();
        UserInfo userInfo = (UserInfo) userService.loadUserByUsername(username);
        result.put("msg", "");
        result.put("code", 200);
        result.put("data", userInfo.getPhone());

        return result;
    }

    /**
     * 修改个人信息
     */
    @Operation(description = "个人信息")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @RequestMapping(path = "/user/{username}", method = RequestMethod.POST)
    public Map modifyUserPhone(@PathVariable("username") String username, @RequestBody Map map) {
        Map<String, Object> result = new HashMap<>();
        boolean success = userService.changePhone(username, (String) map.get("phone"));
        if (success) {
            result.put("msg", "success");
            result.put("code", 200);
        } else {
            result.put("msg", "failed");
            result.put("code", 404);
        }

        return result;
    }


}
