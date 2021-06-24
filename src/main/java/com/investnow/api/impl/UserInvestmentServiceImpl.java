package com.investnow.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.investnow.api.service.UserInvestmentService;
import com.investnow.dao.model.UserInvestment;
import com.investnow.dao.repository.UserInvestmentRepository;

@Service
public class UserInvestmentServiceImpl implements UserInvestmentService
{
    private UserInvestmentRepository userInvestmentRepository;

    @Autowired
    public UserInvestmentServiceImpl(UserInvestmentRepository userInvestmentRepository)
    {
        this.userInvestmentRepository = userInvestmentRepository;
    }

    @Override
    public UserInvestment addUserInvestment(UserInvestment userInvestment)
    {
        return userInvestmentRepository.save(userInvestment);
    }

    @Override
    public List<UserInvestment> getInvestmentsBasedOnUser(String user)
    {
        return userInvestmentRepository.findByUserName(user);
    }

    @Override
    public List<UserInvestment> getInvestmentsBasedOnFund(Long fund)
    {
        return userInvestmentRepository.findByFundId(fund);
    }
}
