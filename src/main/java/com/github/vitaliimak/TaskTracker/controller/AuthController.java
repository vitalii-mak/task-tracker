package com.github.vitaliimak.TaskTracker.controller;

import com.github.vitaliimak.TaskTracker.model.User;
import com.github.vitaliimak.TaskTracker.repository.UserRepository;
import com.github.vitaliimak.TaskTracker.security.jwt.JWTConfigurer;
import com.github.vitaliimak.TaskTracker.security.jwt.TokenProvider;
import com.github.vitaliimak.TaskTracker.service.UserService;
import com.github.vitaliimak.TaskTracker.service.dto.UserDto;
import com.github.vitaliimak.TaskTracker.vm.LoginVM;
import com.github.vitaliimak.TaskTracker.vm.UserVM;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

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

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.getEmail(), loginVM.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(loginVM.getEmail().toLowerCase(Locale.ENGLISH));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(jwt, httpHeaders, HttpStatus.OK);
    }
}
