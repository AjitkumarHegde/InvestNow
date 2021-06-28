package com.investnow.api.service;

import java.util.Set;

import com.investnow.dao.model.Role;

public interface RoleService
{
    /**
     * Fetch all roles
     * @return
     * @throws Exception
     */
    public Set<Role> findRoles() throws Exception;

    /**
     * Fetch role by role name
     * @return
     * @throws Exception
     */
    public Role findRoleByRoleName(String roleName) throws Exception;
}
