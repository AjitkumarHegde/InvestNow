package com.investnow.api.service;

import java.util.List;

import com.investnow.dao.model.Fund;

public interface FundService
{
    /**
     * Fetch the funds from datastore
     * @return
     */
    public List<Fund> getAllFunds();

    /**
     * Add a fund to the datastore
     * @param {@link Fund}
     * @return
     */
    public Fund addFund(Fund fund) throws Exception;

    /**
     * Fetch the funds based on sector name
     * @return
     */
    public List<Fund> getFundsBasedOnSector(String sector);

    /**
     * Fetch the funds based on category name
     * @return
     */
    public List<Fund> getFundsBasedOnCategory(String category);
}
