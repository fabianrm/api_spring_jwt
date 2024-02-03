package com.hampcode.api;


import com.hampcode.security.JwtAuthenticationResponse;
import com.hampcode.dto.LoginRequest;
import com.hampcode.dto.SignUpRequest;
import com.hampcode.dto.UserInfoResponse;
import com.hampcode.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        JwtAuthenticationResponse jwtResponse = authService.authenticateUser(loginRequest);

        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserInfoResponse> registerUser(@RequestBody SignUpRequest signUpRequest) {
        UserInfoResponse userInfoResponse = authService.registerUser(signUpRequest);

        return new ResponseEntity<>(userInfoResponse, HttpStatus.CREATED);
    }
}