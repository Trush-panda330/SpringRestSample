package com.example.rest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.entity.User;
import com.example.domain.mapper.UserMapper;
import com.example.rest.exception.UserCreationException;
import com.example.rest.exception.UserDeletionException;
import com.example.rest.exception.UserNotFoundException;
import com.example.rest.exception.UserUpdateException;
import com.example.rest.response.UserResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * ユーザーサービスの実装クラスです。ユーザー情報の取得、作成、更新、削除などの
 * 操作を提供します。
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private final UserMapper mapper;

	/**
	 * 全てのユーザー情報を取得します。
	 * 
	 * @return すべてのユーザーの情報を含むリスト
	 */
	@Override
	public List<UserResponse> getAllUser() {
		return mapper.findAllUser()
				.stream()
				.map(user -> new UserResponse(
						user.getId(),
						user.getName(),
						user.getAge(),
						user.getAddress()))
				.collect(Collectors.toList());
	}

	/**
	 * ユーザーIDに基づいてユーザー情報を取得します。
	 * 
	 * @param id 検索するユーザーのID
	 * @return 該当するユーザーの情報
	 * @throws UserNotFoundException 指定されたIDのユーザーが見つからない場合にスローされます。
	 */
	@Override
	public UserResponse getUserById(Long id) {
		User user = mapper.findById(id);
		if (user == null) {
			throw new UserNotFoundException("指定されたIDのユーザーは存在しません。ID:" + id);
		}
		return new UserResponse(user.getId(), user.getName(), user.getAge(), user.getAddress());
	}

	/**
	 * 新しいユーザーをデータベースに追加します。
	 * 
	 * @param user 追加するユーザーの情報
	 * @return 登録されたユーザーの情報
	 * @throws UserCreationException ユーザーの作成に失敗した場合にスローされます。
	 */
	@Transactional
	@Override
	public UserResponse createUser(@Valid User user) {
		int rowsAffected = mapper.createUser(user); // ここでIDがセットされる

		if (rowsAffected == 0) {
			throw new UserCreationException("ユーザーの作成に失敗しました。");
		}
		return new UserResponse(user.getId(), user.getName(), user.getAge(), user.getAddress());
	}

	/**
	 * ユーザー情報を更新します。
	 *
	 * @param id 更新するユーザーのID
	 * @param user 更新するユーザー情報
	 * @return 更新されたユーザーの情報
	 * @throws UserUpdateException 指定されたIDのユーザーが見つからない場合にスローされます。
	 */
	@Override
	public UserResponse updateUser(Long id, User user) {
		User existingUser = mapper.findById(id);
		if (existingUser == null) {
			throw new UserUpdateException("指定したユーザーは見つかりませんでした。ID: " + id);
		}

		user.setId(id);
		mapper.updateUser(id, user); // 更新処理

		return new UserResponse(user.getId(), user.getName(), user.getAge(), user.getAddress());
	}

	/**
	 * 指定されたユーザーを削除します。
	 *
	 * @param id 削除するユーザーのID
	 * @return 削除されたユーザーの情報
	 * @throws UserNotFoundException 指定されたIDのユーザーが見つからない場合にスローされます。
	 * @throws UserDeletionException ユーザーの削除に失敗した場合にスローされます。
	 */
	@Transactional
	@Override
	public UserResponse deleteUser(Long id) {
		// ユーザーをIDで検索
		User user = mapper.findById(id);

		// ユーザーが存在しない場合の処理
		if (user == null) {
			logger.warn("ユーザーが見つかりませんでした。指定されたID: {}", id);
			throw new UserNotFoundException("指定されたIDのユーザーは存在しません。ID:" + id);
		}

		try {
			// ユーザーを削除
			mapper.deleteUser(id);
			logger.info("ユーザーが削除されました。ユーザーID: {}", id);
		} catch (Exception ex) {
			// エラー発生時のロギング
			logger.error("ユーザーの削除に失敗しました。ユーザーID：{}", id, ex);
			throw new UserDeletionException("ユーザーの削除に失敗しました。ユーザーID：" + id, ex);
		}

		// 削除したユーザーの情報を返す
		return new UserResponse(user.getId(), user.getName(), user.getAge(), user.getAddress());
	}

}

//package com.example.rest.service;

//@Service
//@RequiredArgsConstructor
//public class UserServiceImpl implements UserService {
//
//	private final UserMapper mapper;
//
//	/**
//	 * 全てのユーザー情報を取得します。
//	 * 
//	 * @return 情報のリスト
//	 */
//	@Override
//	public List<UserResponse> getAllUser() {
//		return mapper.findAllUser()
//				.stream()
//				.map(user -> new UserResponse(
//						user.getId(),
//						user.getName(),
//						user.getAge(),
//						user.getAddress()
//						)).collect(Collectors.toList());
//	}
//
//	/**
//	 * ユーザーIDに基づいてユーザー情報の取得します。
//	 * 
//	 * @param id 検索するユーザーのID
//	 * @return 該当するユーザーの情報。見つからない場合は{@code null} を返す
//	 */
//	@Override
//	public UserResponse getUserById(Long id) {
//		User user = mapper.findById(id);
//		if (user == null) {
//			throw new UserNotFoundException("指定されたIDのユーザーは存在しません。ID:" + id);
//		}
//		return new UserResponse(user.getId(), user.getName(),user.getAge(),user.getAddress());
//	}
//
//	 /**
//     * 新しいユーザーをデータベースに追加します。
//     *
//     * @param user 追加するユーザーの情報
//     * @return 登録されたユーザーの情報
//     */
//    @Transactional
//    public void createUser(@Valid User user) {
//        // データベースにユーザーを追加
//        int rowsAffected = mapper.createUser(user); // ここでIDがセットされる
//
//        // 行数が0の場合、挿入が失敗したことを示す
//        if (rowsAffected == 0) {
//            throw new RuntimeException("ユーザーの作成に失敗しました。");
//        }
//    }
//
//
////	/**
////	 * 指定されたIDのユーザー情報の更新します。
////	 * @param id 更新するユーザーID
////	 * @param user 更新するユーザーの新しい情報
////	 * @return 更新後のユーザー情報
////	 */
////	@Override
////	public User updateUser(Long id, User user) {
////		return mapper.updateUser(id, user);
////	}
//    /**
//     * ユーザー情報を更新します。
//     *
//     * @param id 更新するユーザーのID
//     * @param user 更新されたユーザー情報を含むUserオブジェクト
//     * @return 更新に成功した行数
//     * @throws RuntimeException 指定されたIDのユーザーが見つからない場合にスローされます。
//     */
//    @Override
//    public int updateUser(Long id, User user) {
//        // まずは、更新するユーザーが存在するか確認する
//        User existingUser = mapper.findById(id);
//        if (existingUser == null) {
//            throw new RuntimeException("User not found with id: " + id); // 適切な例外処理を実装
//        }
//
//        // IDをセットして、正しいユーザーを更新
//        user.setId(id);
//        return mapper.updateUser(id, user); // 更新件数を返す
//    }
//
//    @Override
//    public User deleteUser(Long id) {
//        // 削除前にユーザー情報を取得
//        User user = mapper.findById(id);
//        
//        // ユーザーが存在しない場合は例外をスロー
//        if (user == null) {
//            throw new UserNotFoundException("指定されたIDのユーザーは存在しません。ID:" + id);
//        }
//        
//        // ユーザーを削除
//        mapper.deleteUser(id);
//        
//        // 削除されたユーザー情報を返す
//        return user;
//    }
//
//
//}
