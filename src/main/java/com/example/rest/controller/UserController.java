package com.example.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.entity.User;
import com.example.rest.exception.UserNotFoundException;
import com.example.rest.response.UserResponse;
import com.example.rest.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api") // ベースパス
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	/**
	 * 全てのユーザー情報を取得するエンドポイントです。
	 * 
	 * @return 全てのユーザー情報を含むResponseEntity<List<User>>
	 */
	@GetMapping("/user")
	public ResponseEntity<List<UserResponse>> getUsers() {
		//		List<UserResponse> users = userService.getAllUser();
		return ResponseEntity.ok(userService.getAllUser());
	}

	/**
	 * 指定されたIDのユーザー情報を取得するエンドポイントです。
	 * 
	 * @param id ユーザーのID
	 * @return 指定されたIDのユーザー情報を含むResponseEntity<User>
	 * @throws UserNotFoundException 指定されたIDのユーザーが存在しない場合にスローされる
	 */
	@GetMapping("/user/{id}")
	public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}
	//return new ResponseEntity<User> (userService.getUserById(id), HttpStatus.ok); と同じ意味
	//　ResponseEntity.ok(userService.getUserById(id));は明示的にnewとしていないが内部的にインスタンス化している

	/**
	 * 新しいユーザーを登録するエンドポイントです。
	 *
	 * @param user 新規登録するUser情報
	 * @return 新規登録が成功した場合の201のCreated ステータス
	 */
	@PostMapping("/user")
	public ResponseEntity<UserResponse> createUser(@Valid @RequestBody User user) {
		UserResponse createUser= userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
	}

	// ＝＝＝＝＝＝＝ユーザー情報を更新するメソッド（基本的な更新処理）＝＝＝＝＝＝
	//	/**
	//	 * ユーザー情報を更新するエンドポイントです。
	//	 *
	//	 * @param id 更新するユーザーのID
	//	 * @param user 更新されたユーザー情報を含むUserオブジェクト
	//	 * @return 200 OK ステータスと更新件数を含むレスポンス
	//	 * @throws RuntimeException ユーザーが見つからない場合にスローされます。
	//	 */
	//	@PutMapping("/user/{id}")
	//	public ResponseEntity<Integer> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
	//	    int updatedRows = userService.updateUser(id, user); // サービスのメソッドを呼び出す
	//	    return ResponseEntity.ok(updatedRows); // 更新件数を含む200 OKレスポンスを返す
	//	}

	/**
	 * 指定されたIDのユーザー情報を更新するエンドポイント
	 * 
	 * @param id 更新するユーザーのID
	 * @param user 更新内容を含むユーザー情報
	 * @return 更新されたユーザー情報を含むResponseEntity、またはエラーメッセージ
	 */
	@PutMapping("/user/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
		try {
			UserResponse updateUser = userService.updateUser(id, user);
			return ResponseEntity.ok(updateUser);

		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("サーバーでエラーが発生しました。");
		}
	}

	/**
	* 指定されたIDのユーザーを削除します。
	*
	* @param id 削除するユーザーのID
	* @return 削除されたユーザー情報を含むレスポンス。
	*/
	@DeleteMapping("/user/{id}")
	public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id) {
		try {
			UserResponse deleteUser = userService.deleteUser(id);
			return ResponseEntity.ok(deleteUser);
		} catch(UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	
	}

	/**
	 * バリデーションエラーをハンドリングするメソッドです。
	 * 
	 * @param ex バリデーションエラーの例外
	 * @return エラーメッセージを含むResponseEntity
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		// エラーメッセージを生成する
		String errorMessage = ex.getBindingResult().getFieldErrors()
				.stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.collect(Collectors.joining(", "));
		// バリデーションエラーに対して400 Bad Request ステータスとエラーメッセージを返す
		return ResponseEntity.badRequest().body(errorMessage);
	}
	

}
