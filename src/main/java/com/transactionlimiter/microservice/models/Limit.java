package com.transactionlimiter.microservice.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "limits")
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "setting_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ssZ")
    private Date settingDate;
    @Column(name = "account")
    private String account;
    @Column(name = "category")
    private String category;
    @Column(name = "limit_sum")
    private Double limitSum;
    @Column(name = "limit_balance")
    private double limitBalance;

    public Limit() {
    }

    public Limit(Date settingDate, String account, String category, Double limitSum, double limitBalance) {
        this.settingDate = settingDate;
        this.account = account;
        this.category = category;
        this.limitSum = limitSum;
        this.limitBalance = limitBalance;
    }

    public Limit(Date settingDate, String account, String category) {
        this.settingDate = settingDate;
        this.account = account;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getSettingDate() {
        return settingDate;
    }

    public void setSettingDate(Date settingDate) {
        this.settingDate = settingDate;
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

    @Override
    public String toString() {
        return "Limit{" +
                "id=" + id +
                ", settingDate=" + settingDate +
                ", account='" + account + '\'' +
                ", category='" + category + '\'' +
                ", limitSum=" + limitSum +
                ", limitBalance=" + limitBalance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Limit limit = (Limit) o;
        return id == limit.id && Double.compare(limit.limitBalance, limitBalance) == 0 && settingDate.equals(limit.settingDate) && account.equals(limit.account) && category.equals(limit.category) && Objects.equals(limitSum, limit.limitSum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, settingDate, account, category, limitSum, limitBalance);
    }
}
