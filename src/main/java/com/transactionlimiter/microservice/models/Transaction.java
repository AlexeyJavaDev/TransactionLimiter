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
/*    @Column(name = "limit_id")
    private long limitId;*/
    @ManyToOne(cascade = CascadeType.ALL)
    private Limit limit;

    public Transaction() {
    }

    public Transaction(String accountFromId, String accountToId, String currency, double transactionSum, String expenseCategory, Date date, boolean limitExceed, Limit limit) {
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.currency = currency;
        this.transactionSum = transactionSum;
        this.expenseCategory = expenseCategory;
        this.date = date;
        this.limitExceed = limitExceed;
        this.limit = limit;
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

    public Limit getLimitId() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
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
                ", limit=" + limit +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id && Double.compare(that.transactionSum, transactionSum) == 0 && limitExceed == that.limitExceed && limit == that.limit && accountFromId.equals(that.accountFromId) && accountToId.equals(that.accountToId) && currency.equals(that.currency) && expenseCategory.equals(that.expenseCategory) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountFromId, accountToId, currency, transactionSum, expenseCategory, date, limitExceed, limit);
    }
}
