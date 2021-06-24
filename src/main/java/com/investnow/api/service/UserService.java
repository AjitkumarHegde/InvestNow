package com.investnow.api.service;

import com.investnow.dao.model.User;

public interface UserService
{
    /**
     * Add a user
     * @param {@link} User
     * @return
     */
    public User addUser(User user) throws Exception;
}
