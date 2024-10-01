package com.app.presentation.dto;

import javax.validation.constraints.NotNull;

public record AuthRequestLogInDTO (@NotNull String username,@NotNull String password) {
}
