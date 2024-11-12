package com.example.rest.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * ユーザー情報を含むレスポンスクラスです。
 * このクラスはAPIレスポンスとして返されるユーザー情報を表します。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
	
	private Long id;
	
	@NotBlank(message = "名前は必須です。")
	private String name;
	
	@NotNull
	@Min(value = 0, message = "年齢は0歳以上の必要があります。")
	private int age;
	
	@NotBlank(message = "住所は必須です")
	private String address;

}
