package com.example.taskmanagementapp.controller;

import com.example.taskmanagementapp.config.UserAuthProvider;
import com.example.taskmanagementapp.dto.CredentialsDto;
import com.example.taskmanagementapp.dto.SignUpDto;
import com.example.taskmanagementapp.dto.UserDto;
import com.example.taskmanagementapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    public AuthController(UserService userService, UserAuthProvider userAuthProvider) {
        this.userService = userService;
        this.userAuthProvider = userAuthProvider;
    }

    @PostMapping("/login")
    ResponseEntity<UserDto> login(@Valid @RequestBody CredentialsDto credentialsDto) {
        UserDto user = userService.login(credentialsDto);
        user.setToken(userAuthProvider.createToken(user));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    ResponseEntity<UserDto> register(@Valid @RequestBody SignUpDto signUpDto) {
        UserDto user = userService.register(signUpDto);
        user.setToken(userAuthProvider.createToken(user));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
