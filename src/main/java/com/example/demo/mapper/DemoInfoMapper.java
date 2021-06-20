package com.example.demo.mapper;

import com.example.demo.entity.DemoInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DemoInfoMapper {

    @Select("select demo_ID from demo_data;")
    List<String>getAllDemo();

    @Select("select * from demo_data where demo_ID = #{demo_ID};")
    DemoInfo getAllInfoByDemoID(@Param("demo_ID")String demo_ID);

    @Select("select path from demo_data where demo_ID = #{demo_ID};")
    String getPathInfoByDemoID(@Param("demo_ID")String demo_ID);

    @Select("select port from demo_data where demo_ID = #{demo_ID};")
    int getPortInfoByDemoID(@Param("demo_ID")String demo_ID);

}
