package com.investnow.dao.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.investnow.util.GenericUtil;

@Entity
@Table(name = "UserInvestment")
public class UserInvestment extends BaseModel
{
    @Id
    private String transactionId = GenericUtil.generateTransactionId();

    private String userName;

    private Long fundId;

    private String amountInvested;

    private int numberOfYears;

    private String transactionStatus = "COMPLETED";

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getFundId() {
        return fundId;
    }

    public void setFundId(Long fundId) {
        this.fundId = fundId;
    }

    public String getAmountInvested() {
        return amountInvested;
    }

    public void setAmountInvested(String amountInvested) {
        this.amountInvested = amountInvested;
    }

    public int getNumberOfYears() {
        return numberOfYears;
    }

    public void setNumberOfYears(int numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
