package com.example.rest.service;

import com.example.domain.entity.User;
import com.example.domain.mapper.UserRepository;
import com.example.rest.dto.UserDTO;
import com.example.rest.exception.UserCreationException;
import com.example.rest.exception.UserDeletionException;
import com.example.rest.exception.UserNotFoundException;
import com.example.rest.exception.UserUpdateException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ユーザーサービスの実装クラスです。ユーザー情報の取得、作成、更新、削除などの
 * 操作を提供します。
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServiceIF {

//	private static final Logger logger = LoggerFactory.getLogger(UserServiceIF.class);

	private final UserRepository repository;

	/**
	 * 全てのユーザー情報を取得します。
	 * 
	 * @return すべてのユーザーの情報を含むリスト
	 */
	@Override
	public List<UserDTO> getAllUser() {
		return repository.selectAll()
				.stream()
				.map(user -> new UserDTO(
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
	public UserDTO getUserById(Long id) {
		Optional<User> user = repository.selectById(id);
		if (user.isEmpty()) {
			throw new UserNotFoundException("指定されたIDのユーザーは存在しません。ID:" + id);
		}
		return new UserDTO(user.get().getId(), user.get().getName(), user.get().getAge(), user.get().getAddress());
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
	public UserDTO createUser(@Valid User user) {
		int rowsAffected = repository.insert(user); // ここでIDがセットされる

		if (rowsAffected == 0) {
			throw new UserCreationException("ユーザーの作成に失敗しました。");
		}
		return new UserDTO(user.getId(), user.getName(), user.getAge(), user.getAddress());
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
	public UserDTO updateUser(Long id, User user) {
		Optional<User> existingUser = repository.selectById(id);
		if (existingUser.isEmpty()) {
			throw new UserUpdateException("指定したユーザーは見つかりませんでした。ID: " + id);
		}

		user.setId(id);
		repository.update(id, user); // 更新処理

		return new UserDTO(user.getId(), user.getName(), user.getAge(), user.getAddress());
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
	public UserDTO deleteUser(Long id) {
		// ユーザーをIDで検索
		Optional<User> user = repository.selectById(id);
		// 削除したユーザーの情報を返す
		return new UserDTO(user.get().getId(), user.get().getName(), user.get().getAge(), user.get().getAddress());
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
