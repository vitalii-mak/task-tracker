package com.github.vitaliimak.TaskTracker.controller;

import com.github.vitaliimak.TaskTracker.model.User;
import com.github.vitaliimak.TaskTracker.repository.UserRepository;
import com.github.vitaliimak.TaskTracker.service.UserService;
import com.github.vitaliimak.TaskTracker.service.dto.UserDto;
import com.github.vitaliimak.TaskTracker.vm.UserVM;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserVM userVM) throws URISyntaxException {
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userVM.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        UserDto newUser = userService.saveUser(userVM, userVM.getPassword());

        return ResponseEntity.created(new URI("/api/register"))
                .body(newUser);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(userDto -> ResponseEntity.ok().body(userDto))
                .orElse(ResponseEntity.notFound().build());
    }
}
