package com.transactionlimiter.microservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class LimitRequest {
    @NotEmpty(message = "Account should not be empty")
    @Size(min = 10, max = 10, message = "Account should be 10 characters")
    private String account;
    @NotEmpty(message = "Category should not be empty")
    private String category;

    @Min(value = 0, message = "Limit should be positive or 0")
    private double limitSum;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getLimitSum() {
        return limitSum;
    }

    public void setLimitSum(double limitSum) {
        this.limitSum = limitSum;
    }
}
