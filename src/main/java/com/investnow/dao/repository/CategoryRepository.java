package com.investnow.dao.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.investnow.dao.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Serializable>, JpaSpecificationExecutor<Category>
{
    List<Category> findByCategoryName(String categoryName);
}
