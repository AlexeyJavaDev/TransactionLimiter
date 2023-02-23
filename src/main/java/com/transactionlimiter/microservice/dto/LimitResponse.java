package com.transactionlimiter.microservice.dto;

import java.util.Date;

public class LimitResponse {
    private Date settingDate;
    private String category;
    private double limitSum;

    public Date getSettingDate() {
        return settingDate;
    }

    public void setSettingDate(Date settingDate) {
        this.settingDate = settingDate;
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
