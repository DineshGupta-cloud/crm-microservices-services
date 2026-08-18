package com.crm.auth.service;

import com.crm.auth.dto.LoginRequest;
import com.crm.auth.dto.LoginResponse;
import com.crm.auth.dto.RefreshTokenRequest;
import com.crm.auth.dto.RegisterRequest;
import com.crm.auth.entity.Role;
import com.crm.auth.entity.User;
import com.crm.auth.repository.RoleRepository;
import com.crm.auth.repository.UserRepository;
import com.crm.auth.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Transactional
    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Role role = roleRepository.findByName("USER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("USER");
                    return roleRepository.save(newRole);
                });

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.addRole(role);
        return userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsernameIgnoreCase(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(authority -> authority.startsWith("ROLE_") ? authority.substring(5) : authority)
                .toList();

        user.setLastLoginAt(java.time.LocalDateTime.now());
        userRepository.save(user);

        return LoginResponse.builder()
                .accessToken(tokenProvider.generateAccessToken(user.getUsername(), user.getId(), roles))
                .refreshToken(tokenProvider.generateRefreshToken(user.getUsername(), user.getId()))
                .tokenType("Bearer")
                .userId(user.getId())
                .username(user.getUsername())
                .roles(Set.copyOf(roles))
                .build();
    }

    public LoginResponse refresh(RefreshTokenRequest request) {
        if (!tokenProvider.isValid(request.getRefreshToken())) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        var claims = tokenProvider.parseClaims(request.getRefreshToken());
        if (!"refresh".equals(claims.get("type", String.class))) {
            throw new IllegalArgumentException("Token is not a refresh token");
        }

        String username = claims.getSubject();
        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<String> roles = user.getRoles().stream().map(Role::getName).toList();
        return LoginResponse.builder()
                .accessToken(tokenProvider.generateAccessToken(user.getUsername(), user.getId(), roles))
                .refreshToken(tokenProvider.generateRefreshToken(user.getUsername(), user.getId()))
                .tokenType("Bearer")
                .userId(user.getId())
                .username(user.getUsername())
                .roles(Set.copyOf(roles))
                .build();
    }
}
