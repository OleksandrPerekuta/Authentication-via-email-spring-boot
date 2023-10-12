package com.authentication.service;

import com.authentication.exception.UserAlreadyExistsException;
import com.authentication.model.Role;
import com.authentication.model.User;
import com.authentication.model.VerificationToken;
import com.authentication.repository.RoleRepository;
import com.authentication.repository.UserRepository;
import com.authentication.repository.VerificationTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CustomUserDetailsService userDetailsService;
    private final VerificationTokenRepository verificationTokenRepository;

    @Transactional
    public String registerUser(User user) throws Exception {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }
        Role defaultRole = roleRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        user.setRoles(Set.of(defaultRole));
        user.setEmail(user.getEmail().toLowerCase());
        userRepository.save(user);

        return generateVerificationToken(user);
    }
    @Bean
    public void initialRoleValues(){
        roleRepository.save(new Role(1L,"ROLE_ADMIN"));
        roleRepository.save(new Role(2L,"ROLE_USER"));
    }
    private String generateVerificationToken(User user){
        String token = UUID.randomUUID().toString();
        OffsetDateTime expirationDateTime = OffsetDateTime.now().plusHours(1);

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpirationTime(expirationDateTime);
        verificationTokenRepository.save(verificationToken);
        return "http://localhost:8080/register/activate?token=" + token;
    }
    public String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
}
