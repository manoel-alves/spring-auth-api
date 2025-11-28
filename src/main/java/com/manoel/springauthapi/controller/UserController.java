package com.manoel.springauthapi.controller;

import com.manoel.springauthapi.dto.UserChangePasswordRequest;
import com.manoel.springauthapi.dto.UserRegisterRequest;
import com.manoel.springauthapi.dto.UserResponseDTO;
import com.manoel.springauthapi.dto.UserUpdateRequest;
import com.manoel.springauthapi.entity.User;
import com.manoel.springauthapi.exception.UserNotFoundException;
import com.manoel.springauthapi.mapper.UserMapper;
import com.manoel.springauthapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //ENDPOINTS
    // Register
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRegisterRequest request) {
        User user = userService.register(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(location).body(UserMapper.toResponseDTO(user));
    }

    // List all users
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAllUsers() {
        return ResponseEntity.ok(UserMapper.toResponseDTOList(userService.findAllUsers()));
    }

    // Find user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> findByEmail(@PathVariable String  email) {
        return userService.findByEmail(email)
                .map(UserMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(UserNotFoundException::new);
    }

    // update user
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        User user = userService.updateUser(id, request);

        return ResponseEntity.ok(UserMapper.toResponseDTO(user));
    }

    // change password
    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @Valid @RequestBody UserChangePasswordRequest request) {
        userService.changePassword(id, request);

        return ResponseEntity.noContent().build();
    }

    // delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
