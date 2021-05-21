package com.example.demo.mapper;

import com.example.demo.entity.UserInfo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class UserInfoMapperTest {
    @Autowired
    UserInfoMapper userInfoMapper;

    @Test
    void findPasswordByUsername() {
        String password = userInfoMapper.findPasswordByUsername("admin");
        assertNotNull(password);
    }

    @Test
    void findPhoneByUsername() {
        String phone = userInfoMapper.findPhoneByUsername("admin");
        assertNotNull(phone);
    }

    @Test
    void findRoleByUsername() {
        String role = userInfoMapper.findRoleByUsername("admin");
        assertNotNull(role);
    }

    @Test
    void insertUser() {
        UserInfo userInfo = new UserInfo("a", "a", "a", "a", true, true, true, true);
        int res = userInfoMapper.insertUser(userInfo);
        assertEquals(res, 1);
    }

    @Test
    void changePassword() {
        int res = userInfoMapper.changePassword("admin", "123");
        assertEquals(res, 1);
    }

    @Test
    void changePhone() {
        int res = userInfoMapper.changePhone("admin", "123");
        assertEquals(res, 1);
    }
}