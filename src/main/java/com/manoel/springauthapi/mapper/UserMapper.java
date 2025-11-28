package com.manoel.springauthapi.mapper;

import com.manoel.springauthapi.dto.UserResponseDTO;
import com.manoel.springauthapi.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public final class UserMapper {
    private UserMapper() {}

    public static UserResponseDTO toResponseDTO(User user) {
        if (user == null) return null;

        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getRole().name());
    }

    public static List<UserResponseDTO> toResponseDTOList(List<User> users) {
        return users.stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
