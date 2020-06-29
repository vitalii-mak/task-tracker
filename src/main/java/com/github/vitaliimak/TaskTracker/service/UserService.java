package com.github.vitaliimak.TaskTracker.service;

import com.github.vitaliimak.TaskTracker.model.User;
import com.github.vitaliimak.TaskTracker.repository.UserRepository;
import com.github.vitaliimak.TaskTracker.security.SecurityUtils;
import com.github.vitaliimak.TaskTracker.service.dto.UserDto;
import com.github.vitaliimak.TaskTracker.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDto saveUser(UserDto userDto, String password) {
        String encryptedPassword = passwordEncoder.encode(password);

        User newUser = User.builder()
                .email(userDto.getEmail().toLowerCase())
                .password(encryptedPassword)
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .build();
        return userMapper.userToUserDto(userRepository.save(newUser));
    }

    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::userToUserDto);
    }

    public Optional<UserDto> updateUser(UserDto userDto) {
        return userRepository.findOneByEmailIgnoreCase(userDto.getEmail())
                .map(user -> {
                    user.setFirstName(userDto.getFirstName());
                    user.setLastName(userDto.getLastName());
                    return userMapper.userToUserDto(userRepository.save(user));
                });
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::userToUserDto);
    }

    public User getCurrentUser() {
        Optional<String> username = SecurityUtils.getCurrentUserEmail();
        return username.map(s -> userRepository.findOneByEmailIgnoreCase(s).get()).orElse(null);
    }
}
