package com.investnow.api.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.investnow.api.service.FundService;
import com.investnow.dao.model.Fund;
import com.investnow.dao.repository.FundRepository;

@Service
public class FundServiceImpl implements FundService
{
    private FundRepository fundRepository;

    @Autowired
    public FundServiceImpl(FundRepository fundRepository)
    {
        this.fundRepository = fundRepository;
    }

    @Override
    public List<Fund> getAllFunds()
    {
        return fundRepository.findAll();
    }

    @Override
    public Fund addFund(Fund fund) throws Exception
    {
        List<Fund> existingFunds = fundRepository.findByFundName(fund.getFundName());
        if(CollectionUtils.isNotEmpty(existingFunds))
        {
            throw new Exception("Fund " + fund.getFundName() + " already exists");
        }
        return fundRepository.save(fund);

    }

    @Override
    public List<Fund> getFundsBasedOnSector(String sector)
    {
        return fundRepository.findBySector(sector);
    }

    @Override
    public List<Fund> getFundsBasedOnCategory(String category)
    {
        return fundRepository.findByCategory(category);
    }
}
