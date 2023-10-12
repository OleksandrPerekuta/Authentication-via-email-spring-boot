package com.authentication.service;

import com.authentication.exception.UserAlreadyExistsException;
import com.authentication.model.Role;
import com.authentication.model.User;
import com.authentication.repository.RoleRepository;
import com.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CustomUserDetailsService userDetailsService;

    @Transactional
    public UserDetails registerUser(User user) throws Exception {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }
        Role defaultRole = roleRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        user.setRoles(Set.of(defaultRole));
        user.setEmail(user.getEmail().toLowerCase());
        userRepository.save(user);
        return userDetailsService.loadUserByUsername(user.getEmail());
    }
}
