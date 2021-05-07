package com.example.demo.service;

import com.example.demo.entity.UserInfo;
import com.example.demo.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户信息相关服务
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserInfoMapper userInfo_mapper;

    /**
     *
     * @param username 用户名
     * @return 有则返回UserInfo对象，无则返回null
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //find data from database by username
        String password = userInfo_mapper.findPasswordByUsername(username);
        String role = userInfo_mapper.findRoleByUsername(username);
        String phone = userInfo_mapper.findPhoneByUsername(username);

        if(password != null)return new UserInfo(username,password, phone,
                role,true,true,true,true);

        else return null;
    }

    /**
     * 修改号码
     * @param username 用户名
     * @param newphone 新的电话号码
     * @return
     */
    public boolean changePhone(String username,String newphone){
        try{
            userInfo_mapper.changePhone(username,newphone);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 注册
     */
    public boolean registerUser(String username, String password, String phone){
        try {
            UserInfo userInfo = new UserInfo(username,password, phone,"USER",
                    true,true,true,true);
            userInfo_mapper.insertUser(userInfo);
            return true;
        } catch (Exception e){
            return false;
        }

    }
}
