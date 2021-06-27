package com.investnow.config;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.investnow.cache.BlacklistedJwtTokenCache;
import com.investnow.cache.UserLogoutEvent;
import com.investnow.util.Constants;

import io.jsonwebtoken.Jwts;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter
{
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private BlacklistedJwtTokenCache blacklistedJwtTokenCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        try
        {
            String authorization = request.getHeader(Constants.AUTHROIZATION);
            if (StringUtils.isEmpty(authorization) || !authorization.startsWith(Constants.BEARER))
            {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authorization.replace(Constants.BEARER, "");
            if(isValidToken(token))
            {
                String userName = jwtTokenProvider.getUserNameFromJWT(token);
                Set<GrantedAuthority> authorities = jwtTokenProvider.getAuthoritiesFromJWT(token);
                Authentication auth = new UsernamePasswordAuthenticationToken(userName, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        catch (Exception ex)
        {
            response.reset();
            response.getOutputStream().flush();
            response.getOutputStream().print(" Exception:: " + ex.getMessage());
            response.getOutputStream().close();
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Validates if a token satisfies the following properties
     * - Signature is not malformed
     * - Token hasn't expired
     * - Token is supported
     * - Token has not recently been logged out
     * @param authToken {@link String}
     * @throws Exception
     */
    private boolean isValidToken(String authToken) throws Exception
    {
        try
        {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            validateTokenIsNotForALoggedOutDevice(authToken);
        }
        catch (Exception ex)
        {
            throw ex;
        }
        return true;
    }

    private void validateTokenIsNotForALoggedOutDevice(String authToken) throws Exception
    {
        UserLogoutEvent previouslyLoggedOutEvent = blacklistedJwtTokenCache.getLogoutEventForToken(authToken);
        if (previouslyLoggedOutEvent != null)
        {
            String userName = previouslyLoggedOutEvent.getUserName();
            Date logoutEventDate = previouslyLoggedOutEvent.getEventTime();
            String errorMessage = "Token belongs to an already logged out user  " + userName +  " at " + logoutEventDate;
            throw new Exception(errorMessage);
        }
    }
}
