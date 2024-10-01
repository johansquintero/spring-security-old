package com.app.service.interfaces;


import com.app.persistence.entity.UserEntity;
import com.app.presentation.dto.AuthRequestLogInDTO;
import com.app.presentation.dto.AuthRequestSignUpDTO;
import com.app.presentation.dto.AuthResponseDTO;

import java.util.Optional;

public interface IUserService {
    AuthResponseDTO logIn(AuthRequestLogInDTO authRequestLogInDTO);

    AuthResponseDTO signUp(AuthRequestSignUpDTO authRequestSignUpDTO);

}
