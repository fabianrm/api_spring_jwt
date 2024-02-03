package com.hampcode.mapper;

import com.hampcode.dto.LoginRequest;
import com.hampcode.dto.SignUpRequest;
import com.hampcode.dto.UserInfoResponse;
import com.hampcode.model.User;
import com.hampcode.model.UserProfile;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AuthMapper {

    private final ModelMapper modelMapper;

    public AuthMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureSignUpRequestToUserTypeMap();
    }

    //Configuración manual de clase User
    private void configureSignUpRequestToUserTypeMap() {
        TypeMap<SignUpRequest, User> signUpRequestUserTypeMap = modelMapper.
                createTypeMap(SignUpRequest.class, User.class);

        signUpRequestUserTypeMap.addMappings(mapper -> {
            mapper.skip(User::setId); // ID es generado automáticamente
            mapper.map(SignUpRequest::getEmail, User::setEmail);
            mapper.map(SignUpRequest::getPassword, User::setPassword);
            mapper.skip(User::setRoles); // Roles se asignan después
            mapper.skip(User::setUserProfile); // UserProfile se asigna después
        });
    }

    public User signUpRequestToUser(SignUpRequest request) {
        //Mapeo automatico
        User user = modelMapper.map(request, User.class);

        //Mapeo manual
        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName(request.getFirstName());
        userProfile.setLastName(request.getLastName());
        userProfile.setDni(request.getDni());
        userProfile.setUser(user);
        user.setUserProfile(userProfile);

        return user;
    }

    public UserInfoResponse userToUserInfoResponse(User user) {
        // Mapear directamente las propiedades básicas con ModelMapper
        UserInfoResponse response = modelMapper.map(user, UserInfoResponse.class);

        // Usar Optional para manejar UserProfile de manera segura
        Optional.ofNullable(user.getUserProfile()).ifPresent(userProfile -> {
            response.setFirstName(userProfile.getFirstName());
            response.setLastName(userProfile.getLastName());
            response.setDni(userProfile.getDni());
        });

        // Mapear roles
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toList());
        response.setRoles(roles);
        return response;
    }


    public User loginRequestToUser(LoginRequest request) {
        return modelMapper.map(request, User.class);
    }
}
