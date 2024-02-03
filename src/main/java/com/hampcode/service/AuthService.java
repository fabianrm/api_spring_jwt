package com.hampcode.service;

import com.hampcode.security.JwtAuthenticationResponse;
import com.hampcode.dto.LoginRequest;
import com.hampcode.dto.SignUpRequest;
import com.hampcode.dto.UserInfoResponse;

public interface AuthService {
    UserInfoResponse registerUser(SignUpRequest signUpRequest);
    JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest);
}
