package com.investnow.cache;

import java.time.Instant;
import java.util.Date;

import org.springframework.context.ApplicationEvent;

public class UserLogoutEvent extends ApplicationEvent
{
    private final String userName;

    private final String token;

    private final Date eventTime;

    public UserLogoutEvent(String token, String userName)
    {
        super(token);
        this.token = token;
        this.eventTime = Date.from(Instant.now());
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public String getUserName()
    {
        return userName;
    }
}
