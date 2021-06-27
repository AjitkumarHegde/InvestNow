package com.investnow.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UserLogoutEventListener implements ApplicationListener<UserLogoutEvent>
{
    @Autowired
    private BlacklistedJwtTokenCache blacklistedJwtTokenCache;

    @Override
    public void onApplicationEvent(UserLogoutEvent event)
    {
        blacklistedJwtTokenCache.markLogoutEventForToken(event);
    }
}
