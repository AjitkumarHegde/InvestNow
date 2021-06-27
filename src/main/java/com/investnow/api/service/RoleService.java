package com.investnow.api.service;

import java.util.Set;

import com.investnow.dao.model.Role;

public interface RoleService
{
    /**
     * Fetch role by role name
     * @return
     * @throws Exception
     */
    public Set<Role> findRoles() throws Exception;
}
