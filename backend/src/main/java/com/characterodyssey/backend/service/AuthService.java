package com.characterodyssey.backend.service;

import com.characterodyssey.backend.dto.AuthRequest;
import com.characterodyssey.backend.dto.AuthResponse;
import com.characterodyssey.backend.dto.RegisterRequest;
import com.characterodyssey.backend.entity.User;
import com.characterodyssey.backend.repository.UserRepository;
import com.characterodyssey.backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public void register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.username()))
            throw new IllegalArgumentException("Username already taken");
        if (userRepository.existsByEmail(req.email()))
            throw new IllegalArgumentException("Email already registered");

        var user = new User();
        user.setUsername(req.username());
        user.setPassword(passwordEncoder.encode(req.password()));
        user.setEmail(req.email());
        userRepository.save(user);
    }

    public AuthResponse login(AuthRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password()));
        String token = jwtUtil.generateToken(req.username());
        return new AuthResponse(token);
    }
}
