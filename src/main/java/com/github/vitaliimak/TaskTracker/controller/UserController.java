package com.github.vitaliimak.TaskTracker.controller;

import com.github.vitaliimak.TaskTracker.service.UserService;
import com.github.vitaliimak.TaskTracker.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok().body(userService.getAllUsers(pageable));
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto) {
        return userService.updateUser(userDto)
                .map(userDto1 -> ResponseEntity.ok().body(userDto))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
