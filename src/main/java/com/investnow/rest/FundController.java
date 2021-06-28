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

import com.investnow.api.service.FundService;
import com.investnow.dao.model.Fund;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(tags = "FundController", produces = "application/json")
public class FundController
{
    final static Logger logger = LoggerFactory.getLogger(FundController.class);

    @Autowired
    private FundService fundService;

    @RequestMapping(value = "/fund/getAllFunds", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Fetch the list of funds", notes = "Fetch the list of funds", response = Fund.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 403, message = "FORBIDDEN")})
    public ResponseEntity<List<Fund>> getAllFunds()
    {
        return new ResponseEntity(fundService.getAllFunds(), HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/fund/add", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Add a fund", notes = "Add a fund", response = Fund.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 403, message = "FORBIDDEN")})
    public ResponseEntity addFund(@RequestBody @Validated Fund fund)
    {
        try
        {
            logger.debug("addFund() -- Adding a fund");
            return new ResponseEntity(fundService.addFund(fund), HttpStatus.OK);
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>("Exception::" +  ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getFundsBySector", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Fetch the list of funds for a sector", notes = "Fetch the list of funds for a sector", response = Fund.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 403, message = "FORBIDDEN")})
    public ResponseEntity<List<Fund>> getFundsBySector(@RequestParam String sector)
    {
        return new ResponseEntity(fundService.getFundsBasedOnSector(sector), HttpStatus.OK);
    }

    @RequestMapping(value = "/getFundsByCategory", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Fetch the list of funds for a category", notes = "Fetch the list of funds for a category", response = Fund.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<List<Fund>> getFundsByCategory(@RequestParam String category)
    {
        return new ResponseEntity(fundService.getFundsBasedOnCategory(category), HttpStatus.OK);
    }
}
