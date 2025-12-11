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

public interface AuthService {
    public AuthResponse register(RegisterRequest request);

    public AuthResponse authenticate(AuthRequest request);
}
