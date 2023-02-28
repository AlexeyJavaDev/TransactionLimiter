package com.transactionlimiter.microservice.dto;

import com.transactionlimiter.microservice.services.CurrencyPricesService;

import java.util.Date;

public class TransactionResponse {

    private String accountToId;

    private String transactionCurrency;

    private double transactionSum;

    private String expenseCategory;

    private Date transactionDate;

    private Date settingLimitDate;

    private Double limitSum;
    private String limitCurrency;

    public TransactionResponse(String accountToId, String transactionCurrency, double transactionSum, String expenseCategory, Date transactionDate, Date settingLimitDate, Double limitSum) {
        this.accountToId = accountToId;
        this.transactionCurrency = transactionCurrency;
        this.transactionSum = transactionSum;
        this.expenseCategory = expenseCategory;
        this.transactionDate = transactionDate;
        this.settingLimitDate = settingLimitDate;
        this.limitSum = limitSum;
        this.limitCurrency = CurrencyPricesService.getLimitCurrency();
    }

    public String getLimitCurrency() {
        return limitCurrency;
    }

    public void setLimitCurrency(String limitCurrency) {
        this.limitCurrency = limitCurrency;
    }

    public String getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(String accountToId) {
        this.accountToId = accountToId;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(String transactionCurrency) {
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
