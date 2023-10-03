package com.example.taskmanagementapp.controller;

import com.example.taskmanagementapp.dto.PasswordChangeDto;
import com.example.taskmanagementapp.dto.UserAuthDto;
import com.example.taskmanagementapp.dto.UserDto;
import com.example.taskmanagementapp.dto.UserListItemDto;
import com.example.taskmanagementapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    ResponseEntity<List<UserListItemDto>> getUsersAsListItems() {
        return ResponseEntity.ok(
                userService.getUsersAsListItems());
    }

    @GetMapping("")
    ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(
                userService.getUsers());
    }

    @PutMapping("/{id}")
    ResponseEntity<MessageResponse> updateUser(@PathVariable int id, @Valid @RequestBody UserDto userDto) {
        UserDto updated = userService.updateUser(id, userDto);
        return ResponseEntity.ok(new MessageResponse("User with id " + updated.id() + " updated successfully"));
    }

    @PatchMapping("/{id}/toggle")
    ResponseEntity<MessageResponse> toggleUserEnabled(@PathVariable int id) {
        UserDto updated = userService.toggleUserEnabled(id);

        String message = updated.enabled() ?
                "User with id " + updated.id() + " has been enabled" :
                "User with id " + updated.id() + " has been disabled";

        return ResponseEntity.ok(new MessageResponse(message));
    }

    @PatchMapping("/{id}/change-password")
    ResponseEntity<MessageResponse> changePassword(@PathVariable int id, @Valid @RequestBody PasswordChangeDto data) {
        userService.changePassword(id, data);
        return ResponseEntity.ok(new MessageResponse("Password changed successfully"));
    }
}
