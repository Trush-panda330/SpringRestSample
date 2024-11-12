package com.example.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.rest.exception.UserCreationException;
import com.example.rest.exception.UserDeletionException;
import com.example.rest.exception.UserNotFoundException;
import com.example.rest.exception.UserUpdateException;

@RestControllerAdvice 
public class GlobalExceptionHandler {

	/**
	* {@link UserNotFoundException} が発生した場合に呼び出されるメソッドです。
	* 
	* @param userNotFoundException 発生した例外
	* @return NOT_FOUND (404) ステータスと例外メッセージを含むレスポンスエンティティ
	*/
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userNotFoundException.getMessage());
	}

	/**
	* ユーザー作成に失敗した場合に 
	* {@link UserCreationException} が発生したときのエラーハンドリングです。
	 * 
	 * @param userCreationException ユーザー登録に関する例外
	 *  @return INTERNAL_SERVER_ERROR(500)ステータスとエラーメッセージを含むレスポンス。
	 */
	@ExceptionHandler(UserCreationException.class)
	public ResponseEntity<String> handleUserCreationExceptions(UserCreationException userCreationException) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userCreationException.getMessage());
	}

	/**
	 * ユーザーの作成に失敗した場合に
	 *  {@link UserUpdateException}が発生したときにおこるエラーハンドリングです。
	 *  
	 *  @param userUpdateException 発生した例外
	 *  @return INTERNAL_SERVER_ERROR(500)ステータスとエラーメッセージを含むレスポンス。
	 */
	@ExceptionHandler(UserUpdateException.class)
	public ResponseEntity<String> handleUserUpdateExceptions(UserUpdateException userUpdateException) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userUpdateException.getMessage());
	}

	 /**
     * ユーザー情報を削除しようとした際に、削除対象のユーザーが存在しない場合に呼び出されるメソッドです。
     * 
     * @param userDeletionException 発生した例外
     * @return BAD_REQUEST (400) ステータスとエラーメッセージを含むレスポンス
     */
	@ExceptionHandler(UserDeletionException.class)
	public ResponseEntity<String> handleUserDeleteException(UserDeletionException userDeletionException) {
	    // UserDeletionExceptionがユーザーが見つからない場合の例外かどうかをチェックする
	    if ("User not found".equals(userDeletionException.getMessage())) {
	        // 削除対象が存在しない場合
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userDeletionException.getMessage());
	    }
	    // 一般的な削除エラーの場合は404を返す
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userDeletionException.getMessage());
	}


}
