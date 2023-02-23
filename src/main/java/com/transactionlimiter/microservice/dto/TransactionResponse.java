package com.transactionlimiter.microservice.dto;

import java.util.Date;

public class TransactionResponse {
    private String accountFromId;

    private String accountToId;

    private String currency;

    private double transactionSum;

    private String expenseCategory;

    private Date date;
    private Date settingDate;
    private Double limitSum;
}
