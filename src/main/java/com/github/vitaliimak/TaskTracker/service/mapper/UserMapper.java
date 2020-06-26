package com.github.vitaliimak.TaskTracker.service.mapper;

import com.github.vitaliimak.TaskTracker.model.User;
import com.github.vitaliimak.TaskTracker.service.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserDto userToUserDto(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .id(user.getId())
                .build();
    }
}
