package com.example.domain.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.domain.entity.User;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testFindById() {
        Long testId = 1L; // 既存のIDを指定
        User user = userMapper.findById(testId);
        assertNotNull(user); // ユーザーがnullでないことを確認
    }
}
