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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JwtTokenValidator extends OncePerRequestFilter
{
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization) || !authorization.startsWith("bearer"))
        {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.replace("bearer", "");

        try
        {
            Jws<Claims> claims = Jwts.parser().setSigningKey("axb".getBytes()).parseClaimsJws(token);
            Claims body = claims.getBody();
            String username = body.getSubject();
            List<Map<String, String>> authorityMap = (List<Map<String, String>>) body.get("authorities");

            Set<SimpleGrantedAuthority> authorities = authorityMap
                    .stream()
                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                    .collect(Collectors.toSet());

            Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        catch (Exception e)
        {
            logger.error("Exception: ", e);
            response.getWriter().write("{Exception :  " + e.getMessage() + "}");
        }

        filterChain.doFilter(request, response);
    }
}
