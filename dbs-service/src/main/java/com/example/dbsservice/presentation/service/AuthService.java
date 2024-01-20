package com.example.dbsservice.presentation.service;

import com.example.dbsservice.model.dto.LoginDto;
import com.example.dbsservice.model.response.auth.LoginResponse;
import org.springframework.http.ResponseEntity;
import project.api.spec.model.CreateCreateLoginRequestResponse;
import project.api.spec.model.CreateLoginRequest;

public interface AuthService {
    LoginResponse login(CreateLoginRequest request);
}
