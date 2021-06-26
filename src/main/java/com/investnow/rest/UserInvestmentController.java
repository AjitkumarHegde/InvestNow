package com.investnow.rest;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.investnow.api.service.UserInvestmentService;
import com.investnow.dao.model.UserInvestment;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(tags = "UserInvestmentController", produces = "application/json")
public class UserInvestmentController
{
    final static Logger logger = LoggerFactory.getLogger(UserInvestmentController.class);

    @Autowired
    private UserInvestmentService userInvestmentService;

    @RequestMapping(value = "/invest", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Make an investment by providing the required fields", notes = "Make an investment by providing the required fields", response = UserInvestment.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 403, message = "FORBIDDEN")})
    public ResponseEntity invest(@RequestBody @Validated UserInvestment userInvestment)
    {
        try
        {
            logger.debug("invest() -- Adding user investments ");
            return new ResponseEntity(userInvestmentService.addUserInvestment(userInvestment), HttpStatus.OK);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>("Exception::" +  ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getInvestmentsByUser", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Fetch the list of investments for a user", notes = "Fetch the list of investments for a user", response = UserInvestment.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 403, message = "FORBIDDEN")})
    public ResponseEntity<List<UserInvestment>> getInvestmentsByUser(@RequestParam String user)
    {
        return new ResponseEntity(userInvestmentService.getInvestmentsBasedOnUser(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/getInvestmentsByFund", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Fetch the list of investments based on fund", notes = "Fetch the list of investments based on fund", response = UserInvestment.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<List<UserInvestment>> getInvestmentsByFund(@RequestParam String fund)
    {
        return new ResponseEntity(userInvestmentService.getInvestmentsBasedOnFund(Long.valueOf(fund)), HttpStatus.OK);
    }
}
