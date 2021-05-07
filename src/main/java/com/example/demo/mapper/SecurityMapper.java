package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 从数据库中寻找权限与路由相关信息
 */
@Service
@Mapper
public interface SecurityMapper {

    /**
     * 通过url获取能访问该url的权限
     * @param url
     * @return 能够访问该url角色的List集合
     */
    @Select("select role from role_url where url = #{url};")
    List<String> getRoleByUrl(@Param("url") String url);
}
