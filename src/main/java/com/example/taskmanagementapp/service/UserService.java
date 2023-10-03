package com.example.taskmanagementapp.service;

import com.example.taskmanagementapp.dto.*;
import com.example.taskmanagementapp.dto.mapper.UserDtoMapper;
import com.example.taskmanagementapp.exception.InvalidPasswordException;
import com.example.taskmanagementapp.exception.PasswordsDoNotMatchException;
import com.example.taskmanagementapp.exception.UserAlreadyExistsException;
import com.example.taskmanagementapp.exception.UserNotFoundException;
import com.example.taskmanagementapp.model.Role;
import com.example.taskmanagementapp.model.User;
import com.example.taskmanagementapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.taskmanagementapp.dto.mapper.SignUpDtoMapper.mapToUser;
import static com.example.taskmanagementapp.dto.mapper.UserDtoMapper.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserAuthDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByUsername(credentialsDto.username())
                .orElseThrow(UserNotFoundException::new);

        if (!user.isEnabled()) {
            throw new UserNotFoundException();
        }

        if (passwordEncoder.matches(CharBuffer.wrap(
                credentialsDto.password()),
                user.getPassword())) {
            return mapToUserAuthDto(user);
        }

        throw new InvalidPasswordException();
    }

    @Transactional
    public UserAuthDto register(SignUpDto signUpDto) {
        if (!Arrays.equals(signUpDto.password(), signUpDto.passwordConfirmation())) {
            throw new PasswordsDoNotMatchException();
        }

        Optional<User> optionalUser = userRepository.findByUsername(signUpDto.username());

        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        User user = mapToUser(signUpDto);
        user.setRole(Role.USER);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));
        User created = userRepository.save(user);

        return mapToUserAuthDto(created);
    }

    public UserDto getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAuthDto currentUser = (UserAuthDto) authentication.getPrincipal();

        User user = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(UserNotFoundException::new);

        return mapToUserDto(user);
    }

    public List<UserListItemDto> getUsersAsListItems() {
        return userRepository.findAll().stream()
                .map(UserDtoMapper::mapToUserListItemDto)
                .toList();
    }

    public List<UserDto> getUsers() {
        return userRepository.findAll().stream()
                .map(UserDtoMapper::mapToUserDto)
                .toList();
    }

    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        return mapToUserDto(user);
    }

    public UserDto updateUser(int id, UserDto userDto) {
        User toUpdate = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        User updated = userRepository.save(mapToUserUpdate(userDto, toUpdate));
        return mapToUserDto(updated);
    }

    public UserDto toggleUserEnabled(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        user.setEnabled(!user.isEnabled());

        User updated = userRepository.save(user);
        return mapToUserDto(updated);
    }

    public void changePassword(int id, PasswordChangeDto data) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(data.currentPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        if (!data.newPassword().equals(data.newPasswordConfirm())) {
            throw new PasswordsDoNotMatchException();
        }

        user.setPassword(passwordEncoder.encode(data.newPassword()));
        User updated = userRepository.save(user);
        mapToUserDto(updated);
    }
}
