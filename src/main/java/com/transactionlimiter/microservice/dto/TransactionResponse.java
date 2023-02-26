package com.transactionlimiter.microservice.dto;

import java.util.Date;

public class TransactionResponse {

    private String accountToId;

    private String transactionCurrency;

    private double transactionSum;

    private String expenseCategory;

    private Date transactionDate;

    private Date settingLimitDate;

    private Double limitSum;

    public String getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(String accountToId) {
        this.accountToId = accountToId;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(String currency) {
        this.transactionCurrency = transactionCurrency;
    }

    public double getTransactionSum() {
        return transactionSum;
    }

    public void setTransactionSum(double transactionSum) {
        this.transactionSum = transactionSum;
    }

    public String getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(String expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getSettingLimitDate() {
        return settingLimitDate;
    }

    public void setSettingLimitDate(Date settingLimitDate) {
        this.settingLimitDate = settingLimitDate;
    }

    public Double getLimitSum() {
        return limitSum;
    }

    public void setLimitSum(Double limitSum) {
        this.limitSum = limitSum;
    }
}
