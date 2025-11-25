package com.example.REST_API_DATA.serviec;

import com.example.REST_API_DATA.config.JwtService;
import com.example.REST_API_DATA.dto.AuthRequest;
import com.example.REST_API_DATA.dto.AuthResponse;
import com.example.REST_API_DATA.dto.RegisterRequest;
import com.example.REST_API_DATA.entity.Role;
import com.example.REST_API_DATA.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.example.REST_API_DATA.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
                .orElseThrow();
        var jwt = jwtService.generateToken(user);
        return new AuthResponse(jwt);
    }
}
