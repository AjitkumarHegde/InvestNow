package com.investnow.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.investnow.api.service.CategoryService;
import com.investnow.dao.model.Category;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping("/category")
@RestController
@Api(tags = "CategoryController", produces = "application/json")
public class CategoryController
{
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/getAllCategories", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Fetch the list of categories", notes = "Fetch the list of categories", response = Category.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<List<Category>> getAllCategories()
    {
        return new ResponseEntity(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Add a category", notes = "Add a category", response = Category.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")})
    public ResponseEntity addCategory(@RequestBody @Validated Category category)
    {
        try
        {
            return new ResponseEntity(categoryService.addCategory(category), HttpStatus.OK);
        }
        catch(Exception ex)
        {
            return new ResponseEntity<>("Excption while saving the Category - " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
