package com.github.vitaliimak.TaskTracker.controller;

import com.github.vitaliimak.TaskTracker.service.UserService;
import com.github.vitaliimak.TaskTracker.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(userDto -> ResponseEntity.ok().body(userDto))
                .orElse(ResponseEntity.notFound().build());
    }
}
