package com.investnow.api.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.investnow.api.service.RoleService;
import com.investnow.api.service.UserService;
import com.investnow.dao.model.Role;
import com.investnow.dao.model.User;
import com.investnow.dao.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService
{
    private static final String USER_ROLE = "ROLE_USER";

    private UserRepository userRepository;

    private RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService)
    {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public User addUser(User user) throws Exception
    {

        Optional<User> existingUser = userRepository.findByUserName(user.getUsername());
        if(existingUser.isPresent())
        {
            throw new Exception("User " + user.getUsername() + " already exists");
        }

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findRoleByRoleName(USER_ROLE));
        user.addRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<User> userEntry = userRepository.findByUserName(username);
        if(!userEntry.isPresent())
            throw new UsernameNotFoundException("Invalid Login Attempt. User doesn't exist");
        User user = userEntry.get();

        return user;
    }
}
