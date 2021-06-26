package com.investnow.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.investnow.api.service.UserService;
import com.investnow.dao.model.User;
import com.investnow.util.View;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/user")
@RestController
@Api(tags = "UserController", produces = "application/json")
public class UserController
{
    final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
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
            logger.debug("signUp() -- Creating user");
            return new ResponseEntity(userService.addUser(user), HttpStatus.OK);
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>("Exception::" +  ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
