package com.example.taskmanagementapp.dto.mapper;

import com.example.taskmanagementapp.dto.UserAuthDto;
import com.example.taskmanagementapp.dto.UserDto;
import com.example.taskmanagementapp.model.User;

public class UserDtoMapper {

    private UserDtoMapper() {
    }

    public static UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getRole()
        );
    }

    public static UserAuthDto mapToUserAuthDto(User user) {
        return UserAuthDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
