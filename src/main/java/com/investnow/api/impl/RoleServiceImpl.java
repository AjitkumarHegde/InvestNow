package com.investnow.api.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.investnow.api.service.RoleService;
import com.investnow.dao.model.Role;
import com.investnow.dao.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService
{
    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository)
    {
        this.roleRepository = roleRepository;
    }

    @Override
    public Set<Role> findRoles() throws Exception
    {
        List<Role> rolesList = roleRepository.findAll();
        Set<Role> roles = new HashSet<>();
        roles.addAll(rolesList);
        return roles;
    }

    @Override
    public Role findRoleByRoleName(String roleName) throws Exception
    {
        Optional<Role> role = roleRepository.findByRole(roleName);
        if(!role.isPresent())
            throw new Exception("Role not found for the rolename " + roleName);
        else
            return role.get();
    }
}
