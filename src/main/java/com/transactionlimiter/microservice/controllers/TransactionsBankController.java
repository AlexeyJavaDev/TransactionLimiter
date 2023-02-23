package com.transactionlimiter.microservice.controllers;

import com.transactionlimiter.microservice.dto.TransactionRequest;
import com.transactionlimiter.microservice.services.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bank/v1/transactions")
public class TransactionsBankController {
    private final TransactionsService transactionsService;
    @Autowired
    public TransactionsBankController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }
    @PostMapping
    public void saveTransaction(@RequestBody TransactionRequest transactionRequest) {
        transactionsService.saveTransaction(transactionRequest);
    }

}
