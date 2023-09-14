package com.example.taskmanagementapp.dto.mapper;

import com.example.taskmanagementapp.dto.SignUpDto;
import com.example.taskmanagementapp.model.User;

public class SignUpDtoMapper {

    private SignUpDtoMapper() {
    }

    public static User mapToUser(SignUpDto signUpDto) {
        return User.builder()
                .firstName(signUpDto.firstName())
                .lastName(signUpDto.lastName())
                .username(signUpDto.username())
                .build();
    }

}
