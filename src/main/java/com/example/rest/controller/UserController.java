package com.example.rest.controller;

import com.example.domain.entity.User;
import com.example.rest.dto.UserDTO;
import com.example.rest.exception.UserNotFoundException;
import com.example.rest.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	/**
	 * 全てのユーザー情報を取得するエンドポイントです。
	 * 
	 * @return 全てのユーザー情報を含むResponseEntity<List<User>>
	 */
	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserDTO> users = userService.getAllUser();
		return ResponseEntity.ok(users);
	}

	/**
	 * 指定されたIDのユーザー情報を取得するエンドポイントです。
	 * 
	 * @param id ユーザーのID
	 * @return 指定されたIDのユーザー情報を含むResponseEntity<User>
	 */
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
		UserDTO user = userService.getUserById(id);
		return ResponseEntity.ok(user);
	}

	/**
	 * 新しいユーザーを登録するエンドポイントです。
	 *
	 * @param userDTO UserDTO 新規登録するUser情報
	 * @return 新規登録が成功した場合の201のCreated ステータス
	 */
	@PostMapping
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
		userService.createUser(userDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/**
	 * 指定されたIDのユーザー情報を更新するエンドポイント
	 * 
	 * @param id 更新するユーザーのID
	 * @param userDTO UserDTO 更新内容を含むユーザー情報
	 * @return 更新されたユーザー情報を含むResponseEntity
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
		userService.updateUser(id, userDTO);
		return ResponseEntity.ok().build();
	}
	/**
	* 指定されたIDのユーザーを削除します。
	*
	* @param id 削除するユーザーのID
	* @return 削除されたユーザー情報を含むレスポンス。
	*/
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}


}
