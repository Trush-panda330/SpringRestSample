package com.example.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.domain.entity.User;
import com.example.rest.response.UserResponse;


@Service
public interface UserService {

	/**
	 * 全ユーザー情報を取得する。
	 * 
	 * @return UserResponseのリスト
	 */
	List<UserResponse> getAllUser();

	/**
	 * 指定されたIDのユーザー情報を取得する
	 * 
	 * @param id 取得対象のユーザーID
	 * @return 指定IDのUserResponse
	 */
	UserResponse getUserById(Long id);

	/**
	 * 新規ユーザーを作成をする。
	 * 
	 * @param user 追加するユーザー情報
	 */
	UserResponse createUser(User user);
	
	/**
	 * ユーザー情報の更新をする。
	 * 
	 * @param id 更新するユーザーのID
	 * @param user 更新するユーザーの情報
	 * @return 更新したユーザー情報
	 */
	UserResponse updateUser(Long id, User user);

	/**
	 * 指定したIDのユーザー情報の削除。
	 * 
	 * @param id 削除対象のユーザーID
	 * @return 削除したユーザーの情報
	 */
	UserResponse deleteUser(Long id);

}
