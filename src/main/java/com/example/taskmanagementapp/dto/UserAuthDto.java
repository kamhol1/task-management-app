package com.example.taskmanagementapp.dto;

import com.example.taskmanagementapp.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAuthDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private Role role;
    private String token;
}
