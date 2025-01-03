package com.example.domain.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * ユーザーエンティティを表すクラスです。
 * このクラスはデータベースに格納されるユーザー情報を管理します。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	/** ユーザーID*/
	private Long id;
	
	/** ユーザー名*/
	@NotBlank(message = "名前は必須です。")
	private String name;
	
	/** 年齢*/
	@NotNull(message="年齢は必須です。")
	@Min(value = 0,message="年齢は0歳以上で設定してください。")
	private int age;
	
	/** 住所*/
	@NotBlank(message = "住所は必須です。")
	private String address;

}
