package com.hampcode.service.impl;


import com.hampcode.dto.*;
import com.hampcode.exception.BadRequestException;
import com.hampcode.mapper.AuthMapper;
import com.hampcode.model.ERole;
import com.hampcode.model.Role;
import com.hampcode.model.User;
import com.hampcode.repository.UserRepository;
import com.hampcode.security.JwtAuthenticationResponse;
import com.hampcode.security.JwtTokenProvider;
import com.hampcode.security.UserPrincipal;
import com.hampcode.service.AuthService;
import com.hampcode.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final AuthMapper authMapper;
    private final JwtTokenProvider tokenProvider;


    @Override
    public UserInfoResponse registerUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        User user = authMapper.signUpRequestToUser(signUpRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Usar el passwordEncoder inyectado

        // Asignar el rol por defecto
        RoleResponse defaultRoleResponse = roleService.getRoleByName(ERole.ROLE_USER);
        Role defaultRole = new Role(); // Crear un nuevo objeto Role
        defaultRole.setId(defaultRoleResponse.getId());
        defaultRole.setName(ERole.valueOf(String.valueOf(defaultRoleResponse.getName())));

        user.setRoles(Collections.singletonList(defaultRole));

        User savedUser = userRepository.save(user);
        return authMapper.userToUserInfoResponse(savedUser);
    }

    @Override
    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        // Autenticar usuario
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // Establecer autenticación en el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generar token JWT
        String jwt = tokenProvider.generateToken(authentication);

        // Obtener detalles del usuario autenticado
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

        // Crear y devolver JwtAuthenticationResponse con información adicional
        return new JwtAuthenticationResponse(
                jwt,
                "Bearer",
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        );
    }



}
