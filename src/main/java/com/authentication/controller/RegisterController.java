package com.authentication.controller;

import com.authentication.exception.TokenException;
import com.authentication.model.User;
import com.authentication.model.VerificationToken;
import com.authentication.repository.UserRepository;
import com.authentication.repository.VerificationTokenRepository;
import com.authentication.service.RegistrationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.time.OffsetDateTime;

@Slf4j
@RestController
@RequestMapping("/register")
@AllArgsConstructor
public class RegisterController {
    private final RegistrationService registrationService;
    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;
    @PostMapping
    public String registerUser(@RequestBody User user) {

        try {
            String activationUrl=registrationService.registerUser(user);
            return "registration succeed, activate your account, link is here"+activationUrl;
        }catch (Exception e){
            return e.getMessage();
        }
    }
    @GetMapping("/activate")
    public String activateAccount(@RequestParam("token") String token) throws TokenException {
        VerificationToken verificationToken= tokenRepository.findByToken(token).orElseThrow(()->new TokenException("No token found"));
        if (OffsetDateTime.now().isAfter(verificationToken.getExpirationTime())){
            return "token is not valid anymore";
        }
        if (verificationToken.getUser().isEnabled()){
            return "Account is already activated";
        }
        User user =userRepository.findByEmail(verificationToken.getUser().getEmail()).orElseThrow(()->new EntityNotFoundException("User not found with email"+verificationToken.getUser().getEmail()));
        user.setEnabled(true);
        userRepository.save(user);
        return "Account activated";
    }
}
