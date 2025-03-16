package com.emlynma.spring.user.controller;

import com.emlynma.spring.core.ApiResponse;
import com.emlynma.spring.user.contract.request.LoginRequest;
import com.emlynma.spring.user.contract.request.RegisterRequest;
import com.emlynma.spring.user.contract.response.LoginResponse;
import com.emlynma.spring.user.contract.response.RegisterResponse;
import com.emlynma.spring.user.handler.AuthHandler;
import com.emlynma.spring.user.handler.RegisterHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthHandler authHandler;

    private final RegisterHandler registerHandler;

    public ApiResponse<RegisterResponse> register(@Validated @RequestBody RegisterRequest request) {
        return ApiResponse.success(registerHandler.handle(request));
    }

    @RequestMapping("/login")
    public ApiResponse<LoginResponse> login(@Validated @RequestBody LoginRequest request) {
        return ApiResponse.success(authHandler.login(request));
    }

}
