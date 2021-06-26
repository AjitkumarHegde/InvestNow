package com.investnow.config;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investnow.dao.model.User;
import com.investnow.util.Constants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    final static Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private AuthenticationManager authenticationManager;

    private ObjectMapper objectMapper;

    private String jwtSecret;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper, String jwtSecret)
    {
        this.authenticationManager = authenticationManager;
        this.setAuthenticationManager(authenticationManager);
        this.objectMapper = objectMapper;
        this.jwtSecret = jwtSecret;
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    )
            throws IOException, ServletException
    {
        String token = Jwts
                .builder()
                .setSubject(authResult.getName())
                .claim(Constants.AUTHORITIES, authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes())
                .compact();

        response.setHeader(Constants.AUTHROIZATION, Constants.BEARER + token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        User user = new User();
        user.setToken(token);
        user.setUserName(authResult.getName());
        String userData = objectMapper.writeValueAsString(user);
        response.getWriter().write(userData);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try
        {
            Map credentials = objectMapper.readValue(request.getInputStream(), Map.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.get(Constants.USER_NAME),
                            credentials.get(Constants.PASSWORD),
                            new ArrayList<>()
                    )
            );
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException
    {
        response.getWriter().write("{Exception :  " + failed.getMessage() + "}");
    }
}
