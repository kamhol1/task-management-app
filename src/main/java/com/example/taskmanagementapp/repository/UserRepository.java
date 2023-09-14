package com.example.taskmanagementapp.repository;

import com.example.taskmanagementapp.dto.UserDto;
import com.example.taskmanagementapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}