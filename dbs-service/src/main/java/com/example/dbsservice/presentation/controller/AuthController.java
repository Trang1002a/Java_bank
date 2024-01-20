package com.example.dbsservice.presentation.controller;

import com.example.dbsservice.model.response.auth.LoginResponse;
import com.example.dbsservice.presentation.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.api.spec.model.CreateLoginRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody CreateLoginRequest createLoginRequest, HttpServletRequest httpRequest) {
        return authService.login(createLoginRequest);
    }
}
