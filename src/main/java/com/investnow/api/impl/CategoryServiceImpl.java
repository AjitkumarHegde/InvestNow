package com.investnow.api.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.investnow.api.service.CategoryService;
import com.investnow.dao.model.Category;
import com.investnow.dao.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService
{
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories()
    {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) throws Exception
    {
        List<Category> existingCategories = categoryRepository.findByCategoryName(category.getCategoryName());
        if(CollectionUtils.isNotEmpty(existingCategories))
        {
            throw new Exception("Category " + category.getCategoryName() + " already exists");
        }
        return categoryRepository.save(category);
    }
}
