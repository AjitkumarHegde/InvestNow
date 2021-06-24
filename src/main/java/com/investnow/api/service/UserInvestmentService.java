package com.investnow.api.service;

import java.util.List;

import com.investnow.dao.model.UserInvestment;

public interface UserInvestmentService
{
    /**
     * Add a user investment
     * @param {@link} UserInvestment
     * @return
     */
    public UserInvestment addUserInvestment(UserInvestment userInvestment);

    /**
     * Fetch the investments based on user name
     * @param {@link} String user
     * @return
     */
    public List<UserInvestment> getInvestmentsBasedOnUser(String user);

    /**
     * Fetch the investments based on fund name
     * {@link} Long fund
     * @return
     */
    public List<UserInvestment> getInvestmentsBasedOnFund(Long fund);
}
