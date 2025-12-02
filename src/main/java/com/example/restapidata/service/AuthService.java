package com.example.restapidata.service;

import com.example.restapidata.config.JwtService;
import com.example.restapidata.dto.AuthRequest;
import com.example.restapidata.dto.AuthResponse;
import com.example.restapidata.dto.RegisterRequest;
import com.example.restapidata.entity.Role;
import com.example.restapidata.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.example.restapidata.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        var user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return new AuthResponse(jwt);
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        var user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwt = jwtService.generateToken(user);
        return new AuthResponse(jwt);
    }
}
