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
import org.springframework.web.bind.annotation.RestController;

import com.investnow.api.service.SectorService;
import com.investnow.dao.model.Sector;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(tags = "SectorController", produces = "application/json")
public class SectorController
{
    final static Logger logger = LoggerFactory.getLogger(SectorController.class);

    @Autowired
    private SectorService sectorService;

    @RequestMapping(value = "/sector/getAllSectors", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Fetch the list of sectors", notes = "Fetch the list of sectors", response = Sector.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 403, message = "FORBIDDEN")})
    public ResponseEntity<List<Sector>> getAllSectors()
    {
        return new ResponseEntity(sectorService.getAllSectors(), HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/sector/add", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Add a sector", notes = "Add a sector", response = Sector.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "UNAUTHORIZED"),
            @ApiResponse(code = 403, message = "FORBIDDEN")})
    public ResponseEntity addSector(@RequestBody @Validated Sector sector)
    {
        try
        {
            logger.debug("addSector() -- Adding a sector");
            return new ResponseEntity(sectorService.addSector(sector), HttpStatus.OK);
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>("Exception::" +  ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
