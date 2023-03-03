package com.transactionlimiter.microservice.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "limits")
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "setting_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ssZ")
    private Date settingLimitDate;
    @Column(name = "account")
    private String account;
    @Column(name = "category")
    private String category;
    @Column(name = "limit_sum")
    private Double limitSum;
    @Column(name = "limit_balance")
    private double limitBalance;
    @Column(name = "balance_month")
    private int balanceMonth;

    public Limit() {
    }

    public Limit(Date settingLimitDate, String account, String category, int balanceMonth) {
        this.settingLimitDate = settingLimitDate;
        this.account = account;
        this.category = category;
        this.balanceMonth = balanceMonth;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getSettingLimitDate() {
        return settingLimitDate;
    }

    public void setSettingLimitDate(Date settingLimitDate) {
        this.settingLimitDate = settingLimitDate;
    }

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

    public Double getLimitSum() {
        return limitSum;
    }

    public void setLimitSum(Double limitSum) {
        this.limitSum = limitSum;
    }

    public double getLimitBalance() {
        return limitBalance;
    }

    public void setLimitBalance(double limitBalance) {
        this.limitBalance = limitBalance;
    }

    public int getBalanceMonth() {
        return balanceMonth;
    }

    public void setBalanceMonth(int balanceMonth) {
        this.balanceMonth = balanceMonth;
    }
}
