package com.example.restapidata.service;

import com.example.restapidata.config.JwtService;
import com.example.restapidata.dto.AuthRequest;
import com.example.restapidata.dto.AuthResponse;
import com.example.restapidata.entity.Role;
import com.example.restapidata.entity.User;
import com.example.restapidata.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private JwtService jwtService;
    @Mock private AuthenticationManager authenticationManager;

    @InjectMocks private AuthService authService;

    @Test
    @DisplayName("Đăng nhập thành công → trả về token")
    void authenticate_Success_ReturnToken() {

        AuthRequest request = new AuthRequest("admin", "admin123");

        User mockUser = User.builder()
                .username("admin")
                .email("admin@gmail.com")
                .role(Role.ADMIN)
                .build();

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(userRepository.findByUsername("admin"))
                .thenReturn(Optional.of(mockUser));

        when(jwtService.generateToken(mockUser))
                .thenReturn("fake-jwt-token-123");


        AuthResponse response = authService.authenticate(request);


        assertThat(response.token()).isEqualTo("fake-jwt-token-123");

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByUsername("admin");
        verify(jwtService).generateToken(mockUser);
    }

    @Test
    @DisplayName("User không tồn tại → ném UsernameNotFoundException")
    void authenticate_UserNotFound_ThrowsException() {
        AuthRequest request = new AuthRequest("khongtontai", "123");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));

        when(userRepository.findByUsername("khongtontai"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.authenticate(request))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("User not found");

    }
}