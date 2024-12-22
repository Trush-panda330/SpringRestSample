package com.example.domain.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.entity.User;

@Mapper
public interface UserRepository {

	/**
	 * 全ユーザーを取得。
	 *
	 * @return Userのリスト
	 */
	List<User> selectAll();

	/**
	 * IDでユーザーを取得。
	 *
	 * @param id 取得対象のユーザーID
	 * @return User オブジェクト
	 */
	Optional<User> selectById(@Param("id") Long id);

	/**
	 * 新しいユーザーをデータベースに追加します。
	 * 
	 * @param user 追加するユーザーの情報
	 * @return 影響を受けた行数
	 */

	//    @Options(useGeneratedKeys = true, keyProperty = "id")
	//    @Insert("INSERT INTO user (name, age, address) VALUES (#{name}, #{age}, #{address})")
	int insert(User user);

	/**
	* 指定されたIDのユーザー情報を更新します。
	* @param id 更新対象のユーザーID
	* @param user 更新するユーザー情報
	* @return 影響を受けた行数
	*/
	int update(@Param("id") Long id, @Param("user") User user);

	/**
	 * 指定されたIDのユーザーを削除します。
	 *
	 * @param id 削除するユーザーのID
	 * @return 削除されたユーザー情報。ユーザーが存在しない場合はnullを返します。
	 */
	int delete(@Param("id") Long id);
	//	User deleteUser(Long id);
}
