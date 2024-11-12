package com.example.rest.exception;

/**
 * ユーザーが見つからない時にスローされる例外です。
 * RuntimeExceptionを継承しているため
 * 実行時に発生することを考慮しています。
 */
public class UserNotFoundException extends RuntimeException {
	/**
	 * ユーザーが見つからなかった時にエラーメッセージを指定するコンストラクタです。
	 * @param message エラーメッセージ
	 */
	public UserNotFoundException(String message) {
		super(message);
	}

}
