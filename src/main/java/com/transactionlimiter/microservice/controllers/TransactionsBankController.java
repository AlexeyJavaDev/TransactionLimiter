package com.transactionlimiter.microservice.controllers;

import com.transactionlimiter.microservice.dto.TransactionRequest;
import com.transactionlimiter.microservice.services.TransactionsService;
import com.transactionlimiter.microservice.util.ArgumentNotValidException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
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
    public void saveTransaction(@RequestBody @Valid TransactionRequest transactionRequest, BindingResult bindingResult) {   // Incoming transaction from the bank
        if(bindingResult.hasErrors())
            throw new ArgumentNotValidException(bindingResult);
        transactionsService.saveTransaction(transactionRequest);
    }

}
