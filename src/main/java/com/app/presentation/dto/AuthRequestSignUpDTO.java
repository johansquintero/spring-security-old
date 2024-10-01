package com.app.presentation.dto;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
public record AuthRequestSignUpDTO(
        @NotBlank String username,
        @NotBlank String password,
        @NotNull AuthRolesRequestDTO authRolesRequestDto) {
}
