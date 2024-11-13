package com.example.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.todo.entity.User;
import com.example.todo.exception.ResourceNotFoundException;
import com.example.todo.repository.UserRepository;

@Service
public class UserService{

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * すべてのuserを取得する。
     *
     * @return userのリスト
     */
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * userのidを引数にuserを取得する。
     *
     * @param id userのid
     * @return userのリスト
     */
    
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
    }

    /**
     * userのnameを引数にuserを取得する。
     *
     * @param name userのname
     * @return userのリスト
     */
    
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * 新しいuserを作成する。
     *
     * @param user user Object
     * @return 新たに作成されたuser
     */
    
    public User createUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        // Userが作成されたときに必ず roomId:1 のルームに参加するようにする
        return userRepository.save(user);
    }

    /**
     * 既存のuserを更新する。
     *
     * @param id userのid
     * @param user user Object
     * @return 更新されたuser
     */
    
    public User updateUser(Long id, User userDetails) {
        User targetUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User{" + id + "} not found"));
        targetUser.setUsername(userDetails.getUsername());
        targetUser.setEmail(userDetails.getEmail());
        targetUser.setPassword(userDetails.getPassword());
        return userRepository.save(targetUser);
    }

    /**
     * 既存のuserを削除する。
     *
     * @param id userのid
     */
    
    public void deleteUser(Long id) {
        if (!userExists(id)) {
            throw new ResourceNotFoundException("User{" + id + "} not found");
        }
        userRepository.deleteById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean userExists(Long userId){
        return userRepository.existsById(userId);
    }
    
}
