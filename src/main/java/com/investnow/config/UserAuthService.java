package com.investnow.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.investnow.dao.model.User;
import com.investnow.dao.repository.UserRepository;

@Service
public class UserAuthService implements UserDetailsService
{
    private UserRepository userRepository;

    @Autowired
    public UserAuthService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<User> user = userRepository.findByUserName(username);
        return user.orElseThrow(() -> new UsernameNotFoundException("Invalid Login. User doesn't exist"));
    }
}
