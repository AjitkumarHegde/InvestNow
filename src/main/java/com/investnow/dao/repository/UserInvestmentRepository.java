package com.investnow.dao.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.investnow.dao.model.UserInvestment;

@Repository
public interface UserInvestmentRepository extends JpaRepository<UserInvestment, Serializable>, JpaSpecificationExecutor<UserInvestment>
{
   List<UserInvestment> findByUserName(String userName);

   List<UserInvestment> findByFundId(Long fundId);
}
