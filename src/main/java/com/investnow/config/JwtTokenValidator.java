package com.investnow.config;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.investnow.util.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenValidator extends OncePerRequestFilter
{
    @Value("${jwt.secret}")
    protected String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        String authorization = request.getHeader(Constants.AUTHROIZATION);
        if (StringUtils.isEmpty(authorization) || !authorization.startsWith(Constants.BEARER))
        {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.replace(Constants.BEARER, "");

        try
        {
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token);
            Claims body = claims.getBody();
            String username = body.getSubject();
            List<Map<String, String>> authorityMap = (List<Map<String, String>>) body.get(Constants.AUTHORITIES);

            Set<SimpleGrantedAuthority> authorities = authorityMap
                    .stream()
                    .map(m -> new SimpleGrantedAuthority(m.get(Constants.AUTHORITY)))
                    .collect(Collectors.toSet());

            Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        catch (Exception e)
        {
            logger.error("Exception :: ", e);
            response.getWriter().write("{Exception :  " + e.getMessage() + "}");
        }

        filterChain.doFilter(request, response);
    }
}
