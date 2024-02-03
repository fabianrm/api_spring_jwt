package com.hampcode.service;


import com.hampcode.dto.RoleResponse;
import com.hampcode.exception.ResourceNotFoundException;
import com.hampcode.mapper.RoleMapper;
import com.hampcode.model.ERole;
import com.hampcode.model.Role;
import com.hampcode.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements  RoleService{
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleResponse getRoleByName(ERole name) {
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
        return roleMapper.roleToRoleResponse(role);
    }
}
