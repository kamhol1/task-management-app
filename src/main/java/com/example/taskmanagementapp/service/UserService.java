package com.example.taskmanagementapp.service;

import com.example.taskmanagementapp.dto.CredentialsDto;
import com.example.taskmanagementapp.dto.SignUpDto;
import com.example.taskmanagementapp.dto.UserDto;
import com.example.taskmanagementapp.exception.InvalidPasswordException;
import com.example.taskmanagementapp.exception.PasswordsDoNotMatchException;
import com.example.taskmanagementapp.exception.UserAlreadyExistsException;
import com.example.taskmanagementapp.exception.UserNotFoundException;
import com.example.taskmanagementapp.model.User;
import com.example.taskmanagementapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Optional;

import static com.example.taskmanagementapp.dto.mapper.SignUpDtoMapper.mapToUser;
import static com.example.taskmanagementapp.dto.mapper.UserDtoMapper.mapToUserDto;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByUsername(credentialsDto.username())
                .orElseThrow(UserNotFoundException::new);

        if (passwordEncoder.matches(CharBuffer.wrap(
                credentialsDto.password()),
                user.getPassword())) {
            return mapToUserDto(user);
        }

        throw new InvalidPasswordException();
    }

    public UserDto register(SignUpDto signUpDto) {
        if (!Arrays.equals(signUpDto.password(), signUpDto.passwordConfirmation())) {
            throw new PasswordsDoNotMatchException();
        }

        Optional<User> optionalUser = userRepository.findByUsername(signUpDto.username());

        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        User user = mapToUser(signUpDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));
        User created = userRepository.save(user);

        return mapToUserDto(created);
    }
}
