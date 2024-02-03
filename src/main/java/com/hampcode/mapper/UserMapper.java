package com.hampcode.mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.hampcode.dto.UserRequest;
import com.hampcode.dto.UserResponse;
import com.hampcode.model.User;
import com.hampcode.model.UserProfile;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureModelMapper();
    }

    private void configureModelMapper() {
//         Configuración de ModelMapper para UserRequest a User. Dejamos que ModelMapper realice la
//        mapeo automatico porque UserRequest y User tienen propiedades que tienen el mismo nombre
//         y tipo compatible en los objetos fuente y destino
//        modelMapper.typeMap(UserRequest.class, User.class).addMappings(mapper -> {
//            mapper.map(UserRequest::getEmail, User::setEmail);
//            mapper.map(UserRequest::getPassword, User::setPassword);
//        });

        // Configuración adicional para mapear propiedades a UserProfile
        modelMapper.typeMap(UserRequest.class, UserProfile.class).addMappings(mapper -> {
            mapper.map(UserRequest::getFirstName, UserProfile::setFirstName);
            mapper.map(UserRequest::getLastName, UserProfile::setLastName);
            mapper.map(UserRequest::getDni, UserProfile::setDni);
        });

        // Configuración de ModelMapper para User a UserResponse
        modelMapper.typeMap(User.class, UserResponse.class).addMappings(mapper -> {
            mapper.map(User::getId, UserResponse::setId);
            mapper.map(User::getEmail, UserResponse::setEmail);
            mapper.map(src -> src.getUserProfile().getFirstName(), UserResponse::setFirstName);
            mapper.map(src -> src.getUserProfile().getLastName(), UserResponse::setLastName);
            mapper.map(src -> src.getUserProfile().getDni(), UserResponse::setDni);
        });
    }

    public User userRequestToUser(UserRequest request) {
        User user = modelMapper.map(request, User.class);
        UserProfile userProfile = modelMapper.map(request, UserProfile.class);
        userProfile.setUser(user);
        user.setUserProfile(userProfile);
        return user;
    }

    public UserResponse userToUserResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    public List<User> userRequestListToUserList(List<UserRequest> requests) {
        return requests.stream()
                .map(this::userRequestToUser)
                .collect(Collectors.toList());
    }

    public List<UserResponse> userListToUserResponseList(List<User> users) {
        return users.stream()
                .map(this::userToUserResponse)
                .collect(Collectors.toList());
    }
}
