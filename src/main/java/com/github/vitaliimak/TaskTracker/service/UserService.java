package com.github.vitaliimak.TaskTracker.service;

import com.github.vitaliimak.TaskTracker.model.User;
import com.github.vitaliimak.TaskTracker.repository.UserRepository;
import com.github.vitaliimak.TaskTracker.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User saveUser(UserDto userDto, String password) {
        userRepository.findOneByEmailIgnoreCase(userDto.getEmail()).ifPresent(existingUser -> {
            throw new Error("Email " + userDto.getEmail() + " already used");
        });

        String encryptedPassword = passwordEncoder.encode(password);

        User newUser = User.builder()
                .email(userDto.getEmail().toLowerCase())
                .password(encryptedPassword)
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .build();
        return userRepository.save(newUser);
    }

}
