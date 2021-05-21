package com.example.demo.controller;

import com.example.demo.dao.CollectionInfo;
import com.example.demo.entity.Book;
import com.example.demo.entity.Collection;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.BookService;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "用户个人模块")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 注册
     * @param map
     * @return
     */
    @Operation(description = "注册")
    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public Map register(@RequestBody Map map) {
        userService.registerUser((String)map.get("username"),(String)map.get("password"),(String)map.get("phone"));
        Map <String,Object> result = new HashMap<>();
        result.put("msg","success");
        result.put("code",200);
        return result;
    }

    /**
     * 获得个人信息
     * @param username
     * @return
     */
    @Operation(description = "个人信息")
    @RequestMapping(path = "/user/{username}",method = RequestMethod.GET)
    public Map getInfoByUsername(@PathVariable("username")String username){
        Map<String,Object> result =new HashMap();
        UserInfo userInfo = (UserInfo) userService.loadUserByUsername(username);
        result.put("msg","");
        result.put("code",200);
        result.put("data",userInfo.getPhone());

        return result;
    }

    /**
     * 修改个人信息
     * @param username
     * @return
     */
    @Operation(description = "个人信息")
    @RequestMapping(path = "/user/{username}",method = RequestMethod.POST)
    public Map modifyUserPhone(@PathVariable("username")String username, @RequestBody Map map){
        Map<String,Object> result =new HashMap();
        userService.changePhone(username, (String) map.get("phone"));
        result.put("msg","");
        result.put("code",200);

        return result;
    }



}
