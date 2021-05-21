package com.example.demo.mapper;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class SecurityMapperTest {
    @Autowired
    SecurityMapper securityMapper;

    @Test
    void getRoleByUrl() {
        List<String> res = securityMapper.getRoleByUrl("/");
        assertNotNull(res);
    }
}