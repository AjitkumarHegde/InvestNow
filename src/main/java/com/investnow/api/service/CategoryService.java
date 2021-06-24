package com.investnow.api.service;

import java.util.List;

import com.investnow.dao.model.Category;

public interface CategoryService
{
    /**
     * Fetch the categories from datastore
     * @return
     */
    public List<Category> getAllCategories();

    /**
     * Add a category to the datastore
     * @param {@link} Category
     * @return
     */
    public Category addCategory(Category category) throws Exception;
}
