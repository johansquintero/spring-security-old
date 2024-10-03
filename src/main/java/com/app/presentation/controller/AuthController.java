package com.app.presentation.controller;

import com.app.presentation.dto.AuthRequestLogInDTO;
import com.app.presentation.dto.AuthRequestSignUpDTO;
import com.app.presentation.dto.AuthResponseDTO;
import com.app.service.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/auth")
@AllArgsConstructor
public class AuthController {
    private final IUserService userService;

    @PostMapping(path = "/log-in")
    public ResponseEntity<AuthResponseDTO> logIn(@RequestBody @Valid AuthRequestLogInDTO authRequestLogInDTO){
        return ResponseEntity.ok(this.userService.logIn(authRequestLogInDTO));
    }

    @PostMapping(path = "/sign-up")
    public ResponseEntity<AuthResponseDTO> signUp(@RequestBody @Valid AuthRequestSignUpDTO authRequestSignUpDTO){
        System.out.println(authRequestSignUpDTO);
        return ResponseEntity.ok(this.userService.signUp(authRequestSignUpDTO));
    }
}
