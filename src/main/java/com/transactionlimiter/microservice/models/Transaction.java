package com.transactionlimiter.microservice.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "account_from")
    private String accountFromId;
    @Column(name = "account_to")
    private String accountToId;
    @Column(name = "currency_shortname")
    private String currency;
    @Column(name = "transaction_sum")
    private double transactionSum;
    @Column(name = "expense_category")
    private String expenseCategory;
    @Column(name = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ssZ")
    private Date date;
    @Column(name = "limit_exceeded")
    private boolean limitExceed;
    @Column(name = "limit_id")
    private long limitId;

    public Transaction() {
    }

    public Transaction(String accountFromId, String accountToId, String currency, double transactionSum, String expenseCategory, Date date, boolean limitExceed, long limitId) {
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.currency = currency;
        this.transactionSum = transactionSum;
        this.expenseCategory = expenseCategory;
        this.date = date;
        this.limitExceed = limitExceed;
        this.limitId = limitId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public boolean isLimitExceed() {
        return limitExceed;
    }

    public void setLimitExceed(boolean limitExceed) {
        this.limitExceed = limitExceed;
    }

    public long getLimitId() {
        return limitId;
    }

    public void setLimitId(long limitId) {
        this.limitId = limitId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", accountFromId='" + accountFromId + '\'' +
                ", accountToId='" + accountToId + '\'' +
                ", currency='" + currency + '\'' +
                ", transactionSum=" + transactionSum +
                ", expenseCategory='" + expenseCategory + '\'' +
                ", date=" + date +
                ", limitExceed=" + limitExceed +
                ", limitId=" + limitId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id && Double.compare(that.transactionSum, transactionSum) == 0 && limitExceed == that.limitExceed && limitId == that.limitId && accountFromId.equals(that.accountFromId) && accountToId.equals(that.accountToId) && currency.equals(that.currency) && expenseCategory.equals(that.expenseCategory) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountFromId, accountToId, currency, transactionSum, expenseCategory, date, limitExceed, limitId);
    }
}
