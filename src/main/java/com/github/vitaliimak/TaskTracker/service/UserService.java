package com.github.vitaliimak.TaskTracker.service;

import com.github.vitaliimak.TaskTracker.model.User;
import com.github.vitaliimak.TaskTracker.repository.UserRepository;
import com.github.vitaliimak.TaskTracker.service.dto.UserDto;
import com.github.vitaliimak.TaskTracker.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
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
                .map(user -> userMapper.userToUserDto(user));
    }
}
