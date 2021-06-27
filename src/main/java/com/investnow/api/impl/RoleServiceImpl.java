package com.investnow.api.impl;

import java.util.HashSet;
import java.util.List;
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
}
