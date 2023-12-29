package com.mineralidentificationservice.service;

import com.mineralidentificationservice.rest.restMessages.AuthRequest;
import com.mineralidentificationservice.rest.restMessages.AuthenticationResponse;
import com.mineralidentificationservice.rest.restMessages.RegisterRequest;
import com.mineralidentificationservice.model.Role;
import com.mineralidentificationservice.model.UserAccount;
import com.mineralidentificationservice.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserAccountRepository userAccountRepository;
    public final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(registerRequest.getUsername());
        log.info(passwordEncoder.encode(registerRequest.getPassword()));

        userAccount.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userAccount.setRole(Role.USER);
        userAccountRepository.save(userAccount);
        String token = jwtService.generateToken(userAccount);
        return AuthenticationResponse.builder().token(token).build();
    }

    public AuthenticationResponse login(AuthRequest registerRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registerRequest.getUsername(), registerRequest.getPassword()));
        {
            UserAccount userAccount = userAccountRepository.findByUsername(registerRequest.getUsername()).orElseThrow();
            String token = jwtService.generateToken(userAccount);
            return AuthenticationResponse.builder().token(token).build();

        }
    }

}
