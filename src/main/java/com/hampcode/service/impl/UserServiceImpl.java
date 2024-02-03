package com.hampcode.service.impl;

import com.hampcode.dto.UserRequest;
import com.hampcode.dto.UserResponse;
import com.hampcode.exception.ResourceNotFoundException;
import com.hampcode.mapper.UserMapper;
import com.hampcode.model.User;
import com.hampcode.repository.UserRepository;
import com.hampcode.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        User user = userMapper.userRequestToUser(request);
        user = userRepository.save(user);
        return userMapper.userToUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                // .orElseThrow(() -> new RuntimeException("User not found"));
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        updateUserFromRequest(user, request);
        user = userRepository.save(user);
        return userMapper.userToUserResponse(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            //throw new RuntimeException("User not found with id: " + id);
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                // .orElseThrow(() -> new RuntimeException("User not found"));
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.userToUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::userToUserResponse)
                .collect(Collectors.toList());
    }

    private void updateUserFromRequest(User user, UserRequest request) {
        // Actualiza los campos del usuario
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // Considera encriptar la contraseña
        if (user.getUserProfile() != null) {
            user.getUserProfile().setFirstName(request.getFirstName());
            user.getUserProfile().setLastName(request.getLastName());
            user.getUserProfile().setDni(request.getDni());
        }
    }
}
