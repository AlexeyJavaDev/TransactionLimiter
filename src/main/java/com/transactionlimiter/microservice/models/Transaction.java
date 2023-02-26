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
    private String transactionCurrency;
    @Column(name = "transaction_sum")
    private double transactionSum;
    @Column(name = "expense_category")
    private String expenseCategory;
    @Column(name = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ssZ")
    private Date transactionDate;
    @Column(name = "limit_exceeded")
    private boolean limitExceeded;
    @ManyToOne(cascade = CascadeType.ALL)
    private Limit limit;

    public Transaction() {
    }

    public Transaction(String accountFromId, String accountToId, String transactionCurrency, double transactionSum, String expenseCategory, Date transactionDate, boolean limitExceeded, Limit limit) {
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.transactionCurrency = transactionCurrency;
        this.transactionSum = transactionSum;
        this.expenseCategory = expenseCategory;
        this.transactionDate = transactionDate;
        this.limitExceeded = limitExceeded;
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

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(String currency) {
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

    public boolean isLimitExceeded() {
        return limitExceeded;
    }

    public void setLimitExceeded(boolean limitExceeded) {
        this.limitExceeded = limitExceeded;
    }

    public Limit getLimit() {
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
                ", transactionCurrency='" + transactionCurrency + '\'' +
                ", transactionSum=" + transactionSum +
                ", expenseCategory='" + expenseCategory + '\'' +
                ", transactionDate=" + transactionDate +
                ", limitExceeded=" + limitExceeded +
                ", limit=" + limit +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id && Double.compare(that.transactionSum, transactionSum) == 0 && limitExceeded == that.limitExceeded && limit == that.limit && accountFromId.equals(that.accountFromId) && accountToId.equals(that.accountToId) && transactionCurrency.equals(that.transactionCurrency) && expenseCategory.equals(that.expenseCategory) && transactionDate.equals(that.transactionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountFromId, accountToId, transactionCurrency, transactionSum, expenseCategory, transactionDate, limitExceeded, limit);
    }
}
