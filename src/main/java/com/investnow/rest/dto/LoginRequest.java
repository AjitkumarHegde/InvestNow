package com.investnow.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Login Request", description = "The login request payload")
public class LoginRequest
{
    @ApiModelProperty(value = "Registered username", allowableValues = "NonEmpty String", allowEmptyValue = false)
    private String username;

    @ApiModelProperty(value = "Valid user password", required = true, allowableValues = "NonEmpty String")
    private String password;

    public LoginRequest(String username, String email, String password)
    {
        this.username = username;
        this.password = password;
    }

    public LoginRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
