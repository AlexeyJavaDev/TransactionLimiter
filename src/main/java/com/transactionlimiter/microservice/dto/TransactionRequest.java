package com.transactionlimiter.microservice.dto;

import jakarta.validation.constraints.*;

import java.util.Date;

public class TransactionRequest {
    @NotEmpty(message = "Account should not be empty")
    @Size(min = 10, max = 10, message = "Account should be 10 characters")
    private String accountFromId;
    @NotEmpty(message = "Account should not be empty")
    @Size(min = 10, max = 10, message = "Account should be 10 characters")
    private String accountToId;
    @NotEmpty(message = "Currency short name should not be empty")
    @Size(min = 3, max = 3, message = "Currency short name should be 3 characters")
    private String transactionCurrency;
    @Positive(message = "Transaction amount must be greater than 0")
    private double transactionSum;
    @NotEmpty(message = "Category should not be empty")
    private String expenseCategory;
    @Past(message = "Date should not be later than now")
    private Date transactionDate;

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
}
