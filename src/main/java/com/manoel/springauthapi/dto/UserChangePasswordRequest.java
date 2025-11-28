package com.manoel.springauthapi.dto;

import com.manoel.springauthapi.validation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChangePasswordRequest {
    @NotBlank
    private String oldPassword;

    @NotBlank
    @ValidPassword
    private String newPassword;
}
