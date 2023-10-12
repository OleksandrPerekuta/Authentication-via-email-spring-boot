package com.authentication.controller;

import com.authentication.exception.TokenException;
import com.authentication.mail.EmailSenderService;
import com.authentication.mapper.UserMapper;
import com.authentication.model.User;
import com.authentication.model.VerificationToken;
import com.authentication.model.dto.UserDto;
import com.authentication.repository.UserRepository;
import com.authentication.repository.VerificationTokenRepository;
import com.authentication.service.RegistrationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/register")
@AllArgsConstructor
public class RegisterController {
    private final RegistrationService registrationService;
    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailSenderService emailSenderService;
    private static final String ACTIVATION_SUBJECT = "Activate your account for spring boot application";
    private static final String  ACTIVATION_TEXT= "Hello, here is activation link for the account : ";

    @PostMapping
    public String registerUser(@RequestBody @Valid UserDto userDto) {
        try {
            String activationUrl=registrationService.registerUser(userMapper.map(userDto));
            Optional<User> user=userRepository.findByEmail(userDto.getEmail());
            if (user.isPresent()){
                sendEmail(user.get().getEmail(),ACTIVATION_SUBJECT,ACTIVATION_TEXT+activationUrl);
                return "registration succeed, activate your account, link is here : " + activationUrl;
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return "something went wrong";
    }
    private void sendEmail(String receiver,String subject ,String text){
        emailSenderService.sendEmail(receiver,subject,text);
    }

    @GetMapping("/activate")
    public String activateAccount(@RequestParam("token") String token) throws TokenException {
        VerificationToken verificationToken = tokenRepository.findByToken(token).orElseThrow(() -> new TokenException("No token found"));
        if (OffsetDateTime.now().isAfter(verificationToken.getExpirationTime())) {
            return "token is not valid anymore";
        }
        if (verificationToken.getUser().isEnabled()) {
            return "Account is already activated";
        }
        User user = userRepository.findByEmail(verificationToken.getUser().getEmail()).orElseThrow(() -> new EntityNotFoundException("User not found with email" + verificationToken.getUser().getEmail()));
        user.setEnabled(true);
        userRepository.save(user);
        return "Account activated";
    }
}
