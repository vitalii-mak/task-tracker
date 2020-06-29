package com.github.vitaliimak.TaskTracker.security;

import com.github.vitaliimak.TaskTracker.model.User;
import com.github.vitaliimak.TaskTracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component("userDetailsService")
@RequiredArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) {
        Optional<User> userByEmailFromDatabase = userRepository.findOneByEmailIgnoreCase(email);
        return userByEmailFromDatabase.map(this::createSpringSecurityUser)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " was not found in the " +
                        "database"));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(User user) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), new ArrayList<>());
    }
}
