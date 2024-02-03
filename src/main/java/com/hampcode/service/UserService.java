package com.hampcode.service;

import com.hampcode.dto.UserRequest;
import com.hampcode.dto.UserResponse;
import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);
    UserResponse updateUser(Long id, UserRequest request);
    void deleteUser(Long id);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
}
