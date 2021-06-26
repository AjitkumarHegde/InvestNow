package com.investnow.api.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.investnow.api.service.UserService;
import com.investnow.dao.model.User;
import com.investnow.dao.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService
{
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(User user) throws Exception
    {

        Optional<User> existingUser = userRepository.findByUserName(user.getUsername());
        if(existingUser.isPresent())
        {
            throw new Exception("User " + user.getUsername() + " already exists");
        }
        return userRepository.save(user);
    }
}
