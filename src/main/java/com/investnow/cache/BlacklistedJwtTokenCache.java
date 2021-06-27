package com.investnow.cache;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.investnow.config.JwtTokenProvider;

import net.jodah.expiringmap.ExpiringMap;

@Component
public class BlacklistedJwtTokenCache
{
    final static Logger logger = LoggerFactory.getLogger(BlacklistedJwtTokenCache.class);

    private ExpiringMap<String, UserLogoutEvent> tokenEventMap;

    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public BlacklistedJwtTokenCache(JwtTokenProvider jwtTokenProvider)
    {
        this.tokenEventMap = ExpiringMap.builder()
                .variableExpiration()
                .maxSize(1000)
                .build();
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void markLogoutEventForToken(UserLogoutEvent event)
    {
        String token = event.getToken();
        String userName = event.getUserName();
        if (tokenEventMap.containsKey(token))
        {
            logger.info("Logout token already present in the cache for the user {} ", userName);
        }
        else
        {
            Date tokenExpiryDate = jwtTokenProvider.getTokenExpiryFromJWT(token);
            long ttlForToken = getTTLForToken(tokenExpiryDate);
            logger.info("Logout token cache set for {} with a TTL of {} seconds. Token is due expiry at {}", userName, ttlForToken, tokenExpiryDate);
            tokenEventMap.put(token, event, ttlForToken, TimeUnit.SECONDS);
        }
    }

    private long getTTLForToken(Date date)
    {
        long secondAtExpiry = date.toInstant().getEpochSecond();
        long secondAtLogout = Instant.now().getEpochSecond();
        return Math.max(0, secondAtExpiry - secondAtLogout);
    }

    public UserLogoutEvent getLogoutEventForToken(String token)
    {
        return tokenEventMap.get(token);
    }
}
