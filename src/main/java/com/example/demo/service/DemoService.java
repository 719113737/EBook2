package com.example.demo.service;

import com.example.demo.mapper.DemoInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DemoService {

    @Autowired
    DemoInfoMapper demoInfoMapper;

    /**
     * 获得所有的demo
     * @return
     */
    public List<String>getAllDemo(){
        List<String> result = null;

        try{
            result = demoInfoMapper.getAllDemo();
        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 通过demo的id返回端口号
     * @param demo_id
     * @return
     */
    public int getPortByID(String demo_id) {
        int result = -1;

        try{
            result = demoInfoMapper.getPortInfoByDemoID(demo_id);
        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 通过demo的id启动demo
     * @param demo_id
     */
    public boolean executeDemo(String demo_id){
        String path =demoInfoMapper.getPathInfoByDemoID(demo_id);
        //启动exe程序
        try {
            Runtime.getRuntime().exec(path);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
