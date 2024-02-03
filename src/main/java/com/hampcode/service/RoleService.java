package com.hampcode.service;

import com.hampcode.dto.RoleResponse;
import com.hampcode.model.ERole;

public interface RoleService {
    RoleResponse getRoleByName(ERole name);
}
