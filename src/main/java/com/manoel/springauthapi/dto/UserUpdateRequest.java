package com.manoel.springauthapi.dto;

import com.manoel.springauthapi.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    @Email
    private String email;
    @NotBlank
    private Role role;
}
