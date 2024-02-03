package com.hampcode.mapper;


import com.hampcode.dto.RoleRequest;
import com.hampcode.dto.RoleResponse;
import com.hampcode.model.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Role roleRequestToRole(RoleRequest request) {
        return modelMapper.map(request, Role.class);
    }

    public RoleResponse roleToRoleResponse(Role role) {
        return modelMapper.map(role, RoleResponse.class);
    }
}
