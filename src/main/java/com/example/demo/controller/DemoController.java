package com.example.demo.controller;

import com.example.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DemoController {

    @Autowired
    DemoService demoService;

    /**
     * 获得所有demo名称
     * @return
     */
    @GetMapping("/demo")
    public Map getAllDemo(){
        Map<String,Object> result = new HashMap<>();
        List<String> demos = demoService.getAllDemo();
        if(demos != null){
            result.put("code","200");
            result.put("demo",demos);
            result.put("msg","获取成功");
        } else {
            result.put("code","404");
            result.put("msg","获取失败");
        }

        return result;
    }

    /**
     * 创建demo
     * @param demo_id
     * @return
     */
    @PostMapping("/demo/{demo_id}")
    public Map createNewDemo(@PathVariable("demo_id")String demo_id){
        Map<String,Object> result = new HashMap<>();
        boolean flag = demoService.executeDemo(demo_id);

        if(flag) {
            result.put("code","200");
            result.put("msg","创建" + demo_id + "成功");
        }
        else {
            result.put("code","404");
            result.put("msg","创建" + demo_id + "失败");
        }
        return result;
    }
}
