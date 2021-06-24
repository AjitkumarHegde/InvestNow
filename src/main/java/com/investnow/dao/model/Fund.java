package com.investnow.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Fund")
public class Fund extends BaseModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fundId;

    @Column(nullable = false, unique = true)
    private String fundName;

    private String description;

    @Column(nullable = false)
    private String minimumInvestment;

    private String rating;

    @Column(name = "fund_manager", nullable = false)
    private String fundManagerName;

    @Column(nullable = false)
    private String expenseRatio;

    private String exitLoad;

    @Column(nullable = false)
    private String yearlyReturnsPercentage;

    @Column(nullable = false)
    private String fundSize;

    private String category;

    private String sector;

    public Long getFundId() {
        return fundId;
    }

    public void setFundId(Long fundId) {
        this.fundId = fundId;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMinimumInvestment() {
        return minimumInvestment;
    }

    public void setMinimumInvestment(String minimumInvestment) {
        this.minimumInvestment = minimumInvestment;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFundManagerName() {
        return fundManagerName;
    }

    public void setFundManagerName(String fundManagerName) {
        this.fundManagerName = fundManagerName;
    }

    public String getExpenseRatio() {
        return expenseRatio;
    }

    public void setExpenseRatio(String expenseRatio) {
        this.expenseRatio = expenseRatio;
    }

    public String getExitLoad() {
        return exitLoad;
    }

    public void setExitLoad(String exitLoad) {
        this.exitLoad = exitLoad;
    }

    public String getYearlyReturnsPercentage() {
        return yearlyReturnsPercentage;
    }

    public void setYearlyReturnsPercentage(String yearlyReturnsPercentage) {
        this.yearlyReturnsPercentage = yearlyReturnsPercentage;
    }

    public String getFundSize() {
        return fundSize;
    }

    public void setFundSize(String fundSize) {
        this.fundSize = fundSize;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}
