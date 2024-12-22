package com.example.rest.service;

import com.example.domain.entity.User;
import com.example.domain.mapper.UserRepository;
import com.example.rest.dto.UserDTO;
import com.example.rest.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ユーザーサービスの実装クラスです。ユーザー情報の取得、作成、更新、削除などの
 * 操作を提供します。
 */
@Service
@RequiredArgsConstructor
public class UserService {

//	private static final Logger logger = LoggerFactory.getLogger(UserServiceIF.class);

    private final UserRepository userRepository;

    /**
     * 全てのユーザー情報を取得します。
     *
     * @return すべてのユーザーの情報を含むリスト
     */
    public List<UserDTO> getAllUser() {
        return userRepository.selectAll()
                .stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getName(),
                        user.getAge(),
                        user.getAddress()))
                .toList();
    }



    /**
     * ユーザーIDに基づいてユーザー情報を取得します。
     *
     * @param id 検索するユーザーのID
     * @return 該当するユーザーの情報
     * @throws UserNotFoundException 指定されたIDのユーザーが見つからない場合にスローされます。
     */
    public UserDTO getUserById(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("IDは正の整数でなければいけません");
        }
        return userRepository.selectById(id)
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getName(),
                        user.getAge(),
                        user.getAddress()))
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * 新しいユーザーをデータベースに追加します。
     *
     * @param userDTO UserDTO 追加するユーザーの情報
     */
    public void createUser(UserDTO userDTO) {
        userRepository.insert(new User(
                userDTO.getId(),
                userDTO.getName(),
                userDTO.getAge(),
                userDTO.getAddress()));
    }

    /**
     * ユーザー情報を更新します。
     *
     * @param id   更新するユーザーのID
     * @param userDTO UserDTO 更新するユーザー情報
     * @throws UserNotFoundException 指定されたIDのユーザーが見つからない場合にスローされます。
     */
    public void updateUser(Long id, UserDTO userDTO) {
        Optional<User> user = userRepository.selectById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
        }

        userRepository.update(id,new User(
                userDTO.getId(),
                userDTO.getName(),
                userDTO.getAge(),
                userDTO.getAddress()));
    }

    /**
     * 指定されたユーザーを削除します。
     *
     * @param id 削除するユーザーのID
     * @throws UserNotFoundException 指定されたIDのユーザーが見つからない場合にスローされます。
     */
    public void deleteUser(Long id) {
        // ユーザーをIDで検索
        Optional<User> user = userRepository.selectById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        userRepository.delete(id);
    }

}
