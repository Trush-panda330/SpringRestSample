package com.example.rest.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.domain.entity.User;
import com.example.domain.mapper.UserMapper;
import com.example.rest.exception.UserNotFoundException;
import com.example.rest.response.UserResponse;

class UserServiceImplTest {

    // UserMapperインターフェースをモック化。DBへの依存を排除し、任意の戻り値を設定できる
    @Mock 
    private UserMapper userMapper;
    
    // テスト対象のUserServiceImplクラス。モックのuserMapperを自動注入
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    // 各テストメソッドの実行前にモックを初期化する
    @BeforeEach
    void setUp() {
        // Mockitoのモックオブジェクトを初期化
        MockitoAnnotations.openMocks(this);
    }

    // ユーザーが存在する場合のgetUserByIdのテスト
    @Test
    void testGetUserById_Found() {
        // モックの動作を定義。findById(1L)の呼び出しに対してUserオブジェクトを返すよう設定
        User mockUser = new User(1L, "Test User", 25, "Test Address");
        when(userMapper.findById(1L)).thenReturn(mockUser);

        // テスト対象のgetUserByIdを実行し、UserResponseを取得
        UserResponse response = userServiceImpl.getUserById(1L);

        // 結果を検証。期待する値が返ってきているか確認
        assertEquals(1L, response.getId());
        assertEquals("Test User", response.getName());
        assertEquals(25, response.getAge());
        assertEquals("Test Address", response.getAddress());
    }

    // ユーザーが見つからない場合のgetUserByIdのテスト
    @Test
    void testGetUserById_NotFound() {
        // モックの動作を定義。findById(1L)がnullを返すよう設定
        when(userMapper.findById(1L)).thenReturn(null);

        // getUserById実行時にUserNotFoundExceptionが発生するか検証
        assertThrows(UserNotFoundException.class, () -> {
            userServiceImpl.getUserById(1L);
        });
    }

    // 他のメソッドも同様にテストを追加できる
}
