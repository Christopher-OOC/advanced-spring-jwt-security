package com.javalord.app.auth.impl;

import com.javalord.app.auth.AuthenticationService;
import com.javalord.app.auth.request.AuthenticationRequest;
import com.javalord.app.auth.request.RefreshRequest;
import com.javalord.app.auth.request.RegistrationRequest;
import com.javalord.app.auth.response.AuthenticationResponse;
import com.javalord.app.exception.BusinessException;
import com.javalord.app.exception.ErrorCode;
import com.javalord.app.role.Role;
import com.javalord.app.role.RoleRepository;
import com.javalord.app.security.JwtService;
import com.javalord.app.user.User;
import com.javalord.app.user.UserMapper;
import com.javalord.app.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;


    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        final User user = (User) authentication.getPrincipal();
        final String token = this.jwtService.generateAccessToken(user.getUsername());
        final String refreshToken = this.jwtService.generateRefreshToken(user.getUsername());
        final String tokenType = "Bearer";

        return AuthenticationResponse
                .builder()
                .tokenType(tokenType)
                .refreshToken(refreshToken)
                .accessToken(token)
                .build();
    }

    @Override
    @Transactional
    public void register(RegistrationRequest request) {
        checkUserEmail(request.getEmail());
        checkUserPhoneNumber(request.getPhoneNumber());
        checkPasswords(request.getPassword(), request.getConfirmPassword());

        final Role userRole = this.roleRepository
                .findByName("ROLE_USER")
                .orElseThrow(() -> new EntityNotFoundException("Role user does not exists"));

        final List<Role> roles = new ArrayList<>();
        roles.add(userRole);

        final User user = this.userMapper.toUser(request);
        user.setRoles(roles);
        log.debug("Saving user {}", user);
        this.userRepository.save(user);

        final List<User> users = new ArrayList<>();
        users.add(user);
        userRole.setUsers(users);

        this.roleRepository.save(userRole);
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) {
        final String newAccessToken = this.jwtService.generateAccessToken(request.getRefreshToken());
        final String tokenType = "Bearer";

        return AuthenticationResponse
                .builder()
                .accessToken(newAccessToken)
                .refreshToken(tokenType)
                .tokenType(tokenType)
                .build();

    }

    private void checkUserEmail(String email) {
        final boolean exists = this.userRepository.existsByEmailIgnoreCase(email);

        if (exists) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    private void checkUserPhoneNumber(String phoneNumber) {
        final boolean phoneNumberExists = this.userRepository.existsByPhoneNumber(phoneNumber);

        if (phoneNumberExists) {
            throw new BusinessException(ErrorCode.PHONE_ALREADY_EXISTS);
        }

    }

    private void checkPasswords(String password, String confirmPassword) {
        if (password == null || !password.equals(confirmPassword)) {
            throw new BusinessException(ErrorCode.PASSWORDS_MISMATCH);
        }
    }

}
