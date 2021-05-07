package com.example.demo.mapper;

import com.example.demo.entity.UserInfo;
import org.apache.ibatis.annotations.*;

/**
 * 从数据库中寻找和用户相关的信息
 */
@Mapper
public interface UserInfoMapper {

    /**
     * 通过用户名寻找密码
     * @param username 用户名
     * @return 返回密码
     */
    @Select("select password from user_data where username = #{username};")
    String findPasswordByUsername(@Param("username") String username);

    /**
     * 通过用户名寻找手机号
     * @param username 用户名
     * @return 返回手机号
     */
    @Select("select phone from user_data where username = #{username};")
    String findPhoneByUsername(@Param("username") String username);

    /**
     * 通过用户名寻找角色权限
     * @param username 用户名
     * @return 角色权限
     */
    @Select("select role from user_data where username = #{username};")
    String findRoleByUsername(@Param("username") String username);


    /**
     * 向数据库中添加用户信息
     * @return
     */
    @Insert("insert into user_data values(#{username},#{password},#{phone},#{role});")
    int insertUser(UserInfo userInfo);

    /**
     * 修改密码
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Update("update user_data set password = #{password} where username = #{username};")
    int changePassword(@Param("username")String username,@Param("password")String password);

    @Update("update user_data set phone = #{phone} where username = #{username};")
    int changePhone(@Param("username")String username,@Param("phone")String phone);
}