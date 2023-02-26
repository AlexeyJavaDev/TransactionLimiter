package com.transactionlimiter.microservice.dto;

import java.util.Date;

public class TransactionRequest {
    private String accountFromId;

    private String accountToId;

    private String currency;

    private double transactionSum;

    private String expenseCategory;

    private Date date;

    public String getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(String accountFromId) {
        this.accountFromId = accountFromId;
    }

    public String getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(String accountToId) {
        this.accountToId = accountToId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
