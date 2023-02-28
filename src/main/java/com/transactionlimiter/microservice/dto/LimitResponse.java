package com.transactionlimiter.microservice.dto;

import java.util.Date;

public class LimitResponse {
    private Date settingLimitDate;
    private String category;
    private double limitSum;

    public Date getSettingLimitDate() {
        return settingLimitDate;
    }

    public void setSettingLimitDate(Date settingLimitDate) {
        this.settingLimitDate = settingLimitDate;
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
