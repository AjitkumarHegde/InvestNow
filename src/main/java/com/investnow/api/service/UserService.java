package com.investnow.api.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.investnow.dao.model.User;

public interface UserService
{
    /**
     * Add a user
     * @param {@link} User
     * @return
     */
    public User addUser(User user) throws Exception;

    /**
     * Load user details based on username
     * @param username {@link} String
     * @return {@link UserDetails}
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
