package com.investnow.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.investnow.api.service.UserService;
import com.investnow.cache.UserLogoutEvent;
import com.investnow.config.UserAuthService;
import com.investnow.dao.model.User;
import com.investnow.rest.dto.LoginRequest;
import com.investnow.util.Constants;
import com.investnow.util.View;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(tags = "UserController", produces = "application/json")
public class UserController
{
    final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private UserAuthService userAuthService;

    @RequestMapping(value = "/user/signup", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Create a user", notes = "Create a user", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 403, message = "FORBIDDEN")})
    @JsonView(View.User.class)
    public ResponseEntity signUp(@RequestBody @Validated User user)
    {
        try
        {
            return new ResponseEntity(userService.addUser(user), HttpStatus.OK);
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>("Exception::" +  ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "User login", notes = "User login", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 403, message = "FORBIDDEN")})
    public ResponseEntity login(@RequestBody @Validated LoginRequest loginRequest)
    {
        try
        {
            Authentication authentication = userAuthService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
            String token = userAuthService.getUserToken((User) authentication.getPrincipal());

            User authenticatedUser = new User();
            authenticatedUser.setUserName(loginRequest.getUsername());
            authenticatedUser.setToken(token);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, Constants.BEARER + " " + token);
            return new ResponseEntity(authenticatedUser, headers, HttpStatus.OK);
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>("Exception::" +  ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "User logout", notes = "User logout", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 403, message = "FORBIDDEN")})
    public ResponseEntity logout(@RequestParam String userName, @RequestHeader (name="Authorization") String bearer)
    {
        try
        {
            String token = bearer.replace(Constants.BEARER, "");
            UserLogoutEvent userLogoutEvent = new UserLogoutEvent(token, userName);
            applicationEventPublisher.publishEvent(userLogoutEvent);
            return new ResponseEntity<>("Logout Successful for " + userName, HttpStatus.OK);
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>("Exception::" +  ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
