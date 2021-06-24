package com.investnow.dao.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.investnow.dao.model.Fund;

@Repository
public interface FundRepository extends JpaRepository<Fund, Serializable>, JpaSpecificationExecutor<Fund>
{
    List<Fund> findBySector(String sector);

    List<Fund> findByCategory(String category);

    List<Fund> findByFundName(String fundName);
}
