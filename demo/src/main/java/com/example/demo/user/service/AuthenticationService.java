package com.example.demo.user.service;

import com.example.demo.user.dto.LoginRequestDto;
import com.example.demo.user.dto.LoginResponseDto;
import com.example.demo.user.entity.Role;
import com.example.demo.config.JwtService;
import com.example.demo.user.entity.User;
import com.example.demo.user.exception.PasswordIncorrectException;
import com.example.demo.user.exception.UserNotFoundException;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        User user = userRepository.findByName(loginRequestDto.getName())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if (passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {

            LoginResponseDto loginResponseDto = new LoginResponseDto();

            loginResponseDto.setRole(user.getRoles()
                    .stream()
                    .map(Role::getRole)
                    .collect(Collectors.toSet()));

            loginResponseDto.setId(user.getId());
            loginResponseDto.setName(user.getUsername());
            loginResponseDto.setToken(jwtService.generateToken(user));

            return loginResponseDto;
        } else {
            throw new PasswordIncorrectException("Password Incorrect : ");
        }
    }
}
