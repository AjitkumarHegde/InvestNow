package com.investnow.config;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.investnow.dao.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider
{
    private static final String AUTHORITIES_CLAIM = "authorities";

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration:216000000}")
    private long jwtExpirationInSeconds;

    /**
     * Generates a token from a principal object. Embed the refresh token in the jwt
     * so that a new jwt can be created
     * @param user {@link User}
     * @return token
     */
    public String generateToken(User user)
    {
        Instant expiryDate = Instant.now().plusSeconds(jwtExpirationInSeconds);
        String authorities = getUserAuthorities(user);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(expiryDate))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .claim(AUTHORITIES_CLAIM, authorities)
                .compact();
    }

    /**
     * Generates a token from a principal object. Embed the refresh token in the jwt
     * so that a new jwt can be created
     * @param userId {@link Long}
     * @return token
     */
    public String generateTokenFromUserName(String userName)
    {
        Instant expiryDate = Instant.now().plusSeconds(jwtExpirationInSeconds);
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(expiryDate))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Returns the user id encapsulated within the token
     * @param token {@link String}
     * @return userId
     */
    public String getUserNameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * Returns the token expiration date encapsulated within the token
     * @param token {@link String}
     * @return expiration date
     */
    public Date getTokenExpiryFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    /**
     * Return the jwt expiration for the client so that they can execute
     * the refresh token logic appropriately
     * @return expiration duration in seconds
     */
    public long getExpiryDuration() {
        return jwtExpirationInSeconds;
    }

    /**
     * Get the jwt authorities claim encapsulated within the token
     * token {@link String}
     * @return list of authorities
     */
    public Set<GrantedAuthority> getAuthoritiesFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return Arrays.stream(claims.get(AUTHORITIES_CLAIM).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    private String getUserAuthorities(User user)
    {
        return user
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }
}
