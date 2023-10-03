package com.example.taskmanagementapp.dto.mapper;

import com.example.taskmanagementapp.dto.UserAuthDto;
import com.example.taskmanagementapp.dto.UserDto;
import com.example.taskmanagementapp.dto.UserListItemDto;
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
                user.getRole(),
                user.isEnabled()
        );
    }

    public static User mapToUser(UserDto dto) {
        return User.builder()
                .id(dto.id())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .username(dto.username())
                .role(dto.role())
                .enabled(dto.enabled())
                .build();
    }

    public static User mapToUserUpdate(UserDto dto, User toUpdate) {
        toUpdate.setFirstName(dto.firstName());
        toUpdate.setLastName(dto.lastName());
        toUpdate.setUsername(dto.username());
        toUpdate.setRole(dto.role());
        toUpdate.setEnabled(dto.enabled());

        return toUpdate;
    }

    public static UserAuthDto mapToUserAuthDto(User user) {
        return UserAuthDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .build();
    }

    public static UserListItemDto mapToUserListItemDto(User user) {
        return new UserListItemDto(
                user.getId(),
                user.getUsername()
        );
    }
}
