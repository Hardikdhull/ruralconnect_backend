package com.ruralconnect.backend.controller;

import com.ruralconnect.backend.dto.JwtAuthResponse;
import com.ruralconnect.backend.dto.LoginRequest;
import com.ruralconnect.backend.dto.RegisterRequest;
import com.ruralconnect.backend.model.User;
import com.ruralconnect.backend.repository.UserRepository;
import com.ruralconnect.backend.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    // Endpoint: POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // Spring Security does the password check for us
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Get the JWT token
        String jwt = tokenProvider.generateToken(authentication);

        // Get the user's role to send back to the app
        User user = userRepository.findByEmail(loginRequest.getEmail()).get();

        return ResponseEntity.ok(new JwtAuthResponse(jwt, user.getRole()));
    }

    // Endpoint: POST /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {

        // Check if email is already taken
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return new ResponseEntity<>("Email Address already in use!", HttpStatus.BAD_REQUEST);
        }

        // Create new user's account
        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPhoneNumber(registerRequest.getPhoneNumber());

        // Store the HASHED password
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // Give all new users the "USER" role by default
        // Admins will be added manually or with a separate "admin register" endpoint
        user.setRole("ROLE_USER");

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }
}