package com.investnow.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.investnow.dao.model.User;

@Service
public class UserAuthService
{
    private AuthenticationManager authManager;

    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserAuthService(AuthenticationManager authManager, JwtTokenProvider jwtTokenProvider)
    {
        this.authManager = authManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Authenticate user and log them in given a User
     * @param user {@link User}
     * @return {@link Authentication}
     */
    public Authentication authenticateUser(String userName, String password)
    {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return auth;
    }

    /**
     * Generate and return a user token
     * user {@link User}
     */
    public String getUserToken(User user)
    {
        return jwtTokenProvider.generateToken(user);
    }
}
