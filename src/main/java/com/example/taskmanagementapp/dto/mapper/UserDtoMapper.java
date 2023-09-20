package com.example.taskmanagementapp.dto.mapper;

import com.example.taskmanagementapp.dto.UserDto;
import com.example.taskmanagementapp.dto.UserReadDto;
import com.example.taskmanagementapp.model.User;

public class UserDtoMapper {

    private UserDtoMapper() {
    }

    public static UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    public static UserReadDto mapToUserReadDto(User user) {
        return new UserReadDto(
                user.getId(),
                user.getUsername()
        );
    }
}
